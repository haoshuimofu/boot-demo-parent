package com.ddmc.kanban.service.impl;

import com.ddmc.core.model.Pagination;
import com.ddmc.kanban.constant.CacheKeyConstants;
import com.ddmc.kanban.constant.Constants;
import com.ddmc.kanban.dao.kanban.*;
import com.ddmc.kanban.model.*;
import com.ddmc.kanban.response.product.monitor.ProductMonitorItemResponseVo;
import com.ddmc.kanban.response.product.monitor.ProductMonitorSummaryResponseVo;
import com.ddmc.kanban.service.ProductMonitorService;
import com.ddmc.kanban.util.ProductUtil;
import com.ddmc.redis.ICacheManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品过期临期监控
 *
 * @Author wude
 * @Create 2019-03-18 17:34
 */
@Service
public class ProductMonitorServiceImpl implements ProductMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(ProductMonitorServiceImpl.class);

    /**
     * 商品监控时间缓存超时时间
     */
    private static final int MONITOR_REFRESH_TIMEOUT = 8;

    private final StoreProductBatchDao storeProductBatchDao;
    private final ProductDao productDao;
    private final StoreProductBatchTempDao storeProductBatchTempDao;
    private final ProductMonitorDao productMonitorDao;
    private final StoreProductMonitorDao storeProductMonitorDao;
    private final ICacheManager redisCacheManager;
    private final StoreDao storeDao;
    private final VersionDao versionDao;

    public ProductMonitorServiceImpl(StoreProductBatchDao storeProductBatchDao,
                                     ProductDao productDao, StoreProductBatchTempDao storeProductBatchTempDao,
                                     ProductMonitorDao productMonitorDao,
                                     StoreProductMonitorDao storeProductMonitorDao,
                                     ICacheManager redisCacheManager, StoreDao storeDao, VersionDao versionDao) {
        this.storeProductBatchDao = storeProductBatchDao;
        this.productDao = productDao;
        this.storeProductBatchTempDao = storeProductBatchTempDao;
        this.productMonitorDao = productMonitorDao;
        this.storeProductMonitorDao = storeProductMonitorDao;
        this.redisCacheManager = redisCacheManager;
        this.storeDao = storeDao;
        this.versionDao = versionDao;
    }

    @Override
    public boolean tryLockProductMonitor(Date now) {
        return redisCacheManager.putnxCache(CacheKeyConstants.PRODUCT_MONITOR_LAST_REFRESH_TIME,
                String.valueOf(now.getTime()), MONITOR_REFRESH_TIMEOUT * 60);
    }

    @Override
    public void clearProductMonitorLock() {
        redisCacheManager.removeCache(CacheKeyConstants.PRODUCT_MONITOR_LAST_REFRESH_TIME);
    }

    @Override
    public Integer getLastCountTime() {
        return productMonitorDao.selectMaxCountTime();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int clearStoreProductBatchTemp() {
        return storeProductBatchTempDao.deleteAll();
    }

    @Override
    public List<StoreProductBatch> selectStoreProductBatchWithPage(Integer storeId, Integer productId, String batchId, Integer skip, Integer limit) {
        return storeProductBatchDao.selectWithPage(storeId, productId, batchId, skip, limit);
    }

    /**
     * 把store_product_batch根据条件分拣分别存到store_product_batch_temp和store_product_batch_history表
     *
     * @param storeProductBatchList 商品批次记录
     * @param now                   当前时间
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseAndSaveStoreProductBatch2Temp(List<StoreProductBatch> storeProductBatchList, int index, Date now, Map<Integer, Integer> filterStoreIds) throws Exception {
        if (CollectionUtils.isEmpty(storeProductBatchList)) return;
        if (index >= storeProductBatchList.size()) index = storeProductBatchList.size() - 1;
        // 根据productIds查询商品信息，同时对商品进行过滤（不满足条件的商品排除掉）
        List<Integer> productIds = storeProductBatchList.stream()
                .map(StoreProductBatch::getProductId)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, ProductQualityAndSalePeriod> productQualityAndSalePeriodMap = productIds.size() > 0 ? productDao.selectPeriodsByProductIds(productIds).stream()
                .collect(Collectors.toMap(ProductQualityAndSalePeriod::getProductId, Function.identity())) : Collections.emptyMap();
        List<StoreProductBatchTemp> storeProductBatchTemps = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            StoreProductBatch storeProductBatch = storeProductBatchList.get(i);
            if (MapUtils.isEmpty(filterStoreIds) || !filterStoreIds.containsKey(storeProductBatch.getStoreId())) {
                continue;
            }
            ProductQualityAndSalePeriod productQualityAndSalePeriod = productQualityAndSalePeriodMap.get(storeProductBatch.getProductId());
            // 商品过滤，商品在临期过期统计范围中
            if (productQualityAndSalePeriod != null
                    && ProductUtil.isOnMonitor(productQualityAndSalePeriod.getTypeId(), productQualityAndSalePeriod.getStatus(), productQualityAndSalePeriod.getIsBatch())) {
                // 判断商品是否过期临期：过期一定临期，不过期才需要判断临期
                boolean isExpired = ProductUtil.isExpired(storeProductBatch.getBatchId(), productQualityAndSalePeriod.getQualityPeriod(), now);
                boolean isExpiring = isExpired || ProductUtil.isExpiring(storeProductBatch.getBatchId(), productQualityAndSalePeriod.getSalePeriod(), now);
                if (isExpired || isExpiring) {
                    storeProductBatchTemps.add(generateStoreProductBatchTemp(storeProductBatch, productQualityAndSalePeriod));
                }
            }
        }
        if (!storeProductBatchTemps.isEmpty()) {
            logger.info("批量追加store_product_batch_temp记录: " + storeProductBatchTemps.size());
            storeProductBatchTempDao.insertList(storeProductBatchTemps);
        }
    }

    @Override
    public List<StoreProductBatchJoinProductBean> selectStoreProductBatchJoinProductWithPage(Integer storeId, Integer productId, String batchId, Date skipDate, Integer skip, Integer limit) {
        return storeProductBatchDao.selectWithPageAndDate(storeId, productId, batchId, skipDate, skip, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseAndSaveStoreProductBatchJoinProduct2Temp(List<StoreProductBatchJoinProductBean> storeProductBatchJoinProductBeanList, int index, Date now, Map<Integer, Integer> filterStoreIds) throws Exception {
        if (index >= storeProductBatchJoinProductBeanList.size()) {
            index = storeProductBatchJoinProductBeanList.size() - 1;
        }
        List<StoreProductBatchTemp> storeProductBatchTemps = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            StoreProductBatchJoinProductBean storeProductBatchJoinProductBean = storeProductBatchJoinProductBeanList.get(i);
            if (MapUtils.isEmpty(filterStoreIds) || !filterStoreIds.containsKey(storeProductBatchJoinProductBean.getStoreId())) {
                continue;
            }
            // 如果现在批次库存<0，或者不在临期过期商品统计范围中，尝试删除一下
            if (storeProductBatchJoinProductBean.getAmount().doubleValue() <= 0
                    || !ProductUtil.isOnMonitor(storeProductBatchJoinProductBean.getTypeId(), storeProductBatchJoinProductBean.getStatus(), storeProductBatchJoinProductBean.getIsBatch())) {
                storeProductBatchTempDao.deleteByStoreProductId(storeProductBatchJoinProductBean.getStoreId(), storeProductBatchJoinProductBean.getProductId());
            } else {
                boolean isExpired = ProductUtil.isExpired(storeProductBatchJoinProductBean.getBatchId(), storeProductBatchJoinProductBean.getQualityPeriod(), now);
                boolean isExpiring = isExpired || ProductUtil.isExpiring(storeProductBatchJoinProductBean.getBatchId(), storeProductBatchJoinProductBean.getSalePeriod(), now);
                if (isExpired || isExpiring) {
                    StoreProductBatchTemp storeProductBatchTemp
                            = storeProductBatchTempDao.selectStoreProductBatch(storeProductBatchJoinProductBean.getStoreId(), storeProductBatchJoinProductBean.getProductId(), storeProductBatchJoinProductBean.getBatchId());
                    if (storeProductBatchTemp == null) {
                        storeProductBatchTemps.add(generateStoreProductBatchTemp(storeProductBatchJoinProductBean));
                    } else {
                        // 如果商品库存、保质期天数、可售期天数、商品批次更新时间、则更新，反之跳过
                        if (storeProductBatchJoinProductBean.getAmount().compareTo(storeProductBatchTemp.getAmount()) != 0
                                || !storeProductBatchJoinProductBean.getProductUpdateTime().equals(storeProductBatchTemp.getBatchUpdateTime())
                                || !storeProductBatchTemp.getQualityPeriod().equals(storeProductBatchJoinProductBean.getQualityPeriod())
                                || !storeProductBatchTemp.getSalePeriod().equals(storeProductBatchJoinProductBean.getSalePeriod())) {
                            StoreProductBatchTemp updateObj = new StoreProductBatchTemp();
                            updateObj.setId(storeProductBatchTemp.getId());
                            updateObj.setAmount(storeProductBatchJoinProductBean.getAmount());
                            updateObj.setBatchUpdateTime(storeProductBatchJoinProductBean.getBatchUpdateTime());
                            updateObj.setQualityPeriod(storeProductBatchJoinProductBean.getQualityPeriod());
                            updateObj.setSalePeriod(storeProductBatchJoinProductBean.getSalePeriod());
                            if (!storeProductBatchTemp.getQualityPeriod().equals(storeProductBatchJoinProductBean.getQualityPeriod())) {
                                updateObj.setQualityDate(ProductUtil.getQualityDate(storeProductBatchTemp.getBatchId(), storeProductBatchJoinProductBean.getQualityPeriod()));
                            }
                            if (!storeProductBatchTemp.getSalePeriod().equals(storeProductBatchJoinProductBean.getSalePeriod())) {
                                updateObj.setSaleDate(ProductUtil.getSaleDate(storeProductBatchTemp.getBatchId(), storeProductBatchJoinProductBean.getSalePeriod()));
                            }
                            updateObj.setUpdateTime(new Date());
                            storeProductBatchTempDao.update(updateObj);
                        }
                    }
                } else {
                    storeProductBatchTempDao.deleteByStoreProductId(storeProductBatchJoinProductBean.getStoreId(), storeProductBatchJoinProductBean.getProductId());
                }
            }
        }
        if (!storeProductBatchTemps.isEmpty()) {
            logger.info("批量追加store_product_batch_temp记录: " + storeProductBatchTemps.size());
            storeProductBatchTempDao.insertList(storeProductBatchTemps);
        }
    }

    /**
     * 生成门店商品批次中间表记录
     *
     * @param storeProductBatch
     * @param productQualityAndSalePeriod
     * @return
     * @throws Exception
     */
    private StoreProductBatchTemp generateStoreProductBatchTemp(StoreProductBatch storeProductBatch, ProductQualityAndSalePeriod productQualityAndSalePeriod) throws Exception {
        StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
        storeProductBatchTemp.setStoreId(storeProductBatch.getStoreId());
        storeProductBatchTemp.setProductId(storeProductBatch.getProductId());
        storeProductBatchTemp.setBatchId(storeProductBatch.getBatchId());
        storeProductBatchTemp.setAmount(storeProductBatch.getAmount());
        storeProductBatchTemp.setQualityPeriod(productQualityAndSalePeriod.getQualityPeriod());
        storeProductBatchTemp.setSalePeriod(productQualityAndSalePeriod.getSalePeriod());
        storeProductBatchTemp.setQualityDate(ProductUtil.getQualityDate(storeProductBatch.getBatchId(), productQualityAndSalePeriod.getQualityPeriod()));
        storeProductBatchTemp.setSaleDate(ProductUtil.getSaleDate(storeProductBatch.getBatchId(), productQualityAndSalePeriod.getSalePeriod()));
        storeProductBatchTemp.setBatchUpdateTime(storeProductBatch.getUpdateTime());
        storeProductBatchTemp.setCreateTime(new Date());
        return storeProductBatchTemp;
    }

    /**
     * 生成门店商品批次中间表记录
     *
     * @param storeProductBatchJoinProductBean
     * @return
     * @throws Exception
     */
    private StoreProductBatchTemp generateStoreProductBatchTemp(StoreProductBatchJoinProductBean storeProductBatchJoinProductBean) throws Exception {
        StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
        storeProductBatchTemp.setStoreId(storeProductBatchJoinProductBean.getStoreId());
        storeProductBatchTemp.setProductId(storeProductBatchJoinProductBean.getProductId());
        storeProductBatchTemp.setBatchId(storeProductBatchJoinProductBean.getBatchId());
        storeProductBatchTemp.setAmount(storeProductBatchJoinProductBean.getAmount());
        storeProductBatchTemp.setQualityPeriod(storeProductBatchJoinProductBean.getQualityPeriod());
        storeProductBatchTemp.setSalePeriod(storeProductBatchJoinProductBean.getSalePeriod());
        storeProductBatchTemp.setQualityDate(ProductUtil.getQualityDate(storeProductBatchJoinProductBean.getBatchId(), storeProductBatchJoinProductBean.getQualityPeriod()));
        storeProductBatchTemp.setSaleDate(ProductUtil.getSaleDate(storeProductBatchJoinProductBean.getBatchId(), storeProductBatchJoinProductBean.getSalePeriod()));
        storeProductBatchTemp.setBatchUpdateTime(storeProductBatchJoinProductBean.getBatchUpdateTime());
        storeProductBatchTemp.setCreateTime(new Date());
        return storeProductBatchTemp;
    }

    /**
     * kanban.store_product_batch_temp中临期过期商品批次记录抽取门店过期临期商品数，其实就是批次合并
     *
     * @param time
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer syncStoreProductBatchTemp2StoreProductMonitor(Date time) {
        int skuSum = storeProductBatchTempDao.countSkuNum();
        // 生成新的数据版本
        Integer version = null;
        Version queryObj = new Version();
        queryObj.setTaskName(Constants.PRODUCT_MONITOR_TASK_NAME_VERSION);
        while (true) {
            Version resultObj = versionDao.selectOne(queryObj);
            if (versionDao.casUpdateVersion(Constants.PRODUCT_MONITOR_TASK_NAME_VERSION, resultObj.getVersion()) > 0) {
                version = resultObj.getVersion() + 1;
                break;
            }
        }
        List<Integer> types = Arrays.asList(1, 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Integer skipDate = Integer.valueOf(format.format(time));
        format = new SimpleDateFormat("yyyyMMddHH");
        for (Integer type : types) {
            int total = 0;
            int page = 0;
            int pageSize = Constants.PRODUCT_MONITOR_TASK_PAGE_SIZE;
            Integer storeId = null;
            Integer productId = null;
            while (true) {
                int skip = page * pageSize;
                List<StoreProductMonitor> storeProductMonitors = storeProductBatchTempDao.selectGroupByStoreProductWithPage(storeId, productId, type, skipDate, skip, pageSize + 1);
                if (CollectionUtils.isEmpty(storeProductMonitors)) break;
                if (storeProductMonitors.size() <= pageSize) {
                    total += generateProductMonitor(storeProductMonitors, storeProductMonitors.size() - 1, type, version);
                    break;
                } else {
                    total += generateProductMonitor(storeProductMonitors, storeProductMonitors.size() - 2, type, version);
                    StoreProductMonitor lastOne = storeProductMonitors.get(storeProductMonitors.size() - 1);
                    storeId = lastOne.getStoreId();
                    productId = lastOne.getProductId();
                }
            }
            ProductMonitor productMonitor = new ProductMonitor();
            productMonitor.setType(type);
            productMonitor.setTotal(total);
            productMonitor.setSkuSum(skuSum);
            productMonitor.setCreateTime(new Date());
            productMonitor.setExeStartTime(time);
            productMonitor.setExeEndTime(new Date());
            productMonitor.setVersion(version);
            productMonitor.setCountTime(Integer.valueOf(format.format(time)));
            productMonitorDao.insert(productMonitor);
        }
        return version;
    }

    private int generateProductMonitor(List<StoreProductMonitor> storeProductMonitors, int index, Integer type, Integer version) {
        if (index >= storeProductMonitors.size()) {
            index = storeProductMonitors.size() - 1;
        }
        int total = 0;
        for (int i = 0; i <= index; i++) {
            StoreProductMonitor storeProductMonitor = storeProductMonitors.get(i);
            storeProductMonitor.setType(type);
            storeProductMonitor.setCreateTime(new Date());
            storeProductMonitor.setVersion(version);
            total += storeProductMonitor.getTotal();
        }
        storeProductMonitorDao.insertList(storeProductMonitors.subList(0, index + 1));
        return total;
    }

    @Override
    public ProductMonitorSummaryResponseVo getProductMonitorySummary(Integer version) throws Exception {
        ProductMonitorSummaryResponseVo responseVo = new ProductMonitorSummaryResponseVo();
        ProductMonitor queryObj = new ProductMonitor();
        queryObj.setVersion(version);
        List<ProductMonitor> productMonitors = productMonitorDao.selectList(queryObj);
        SimpleDateFormat parser = new SimpleDateFormat("yyyyMMddHH");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ProductMonitor productMonitor : productMonitors) {
            responseVo.setSkuSum(productMonitor.getSkuSum());
            responseVo.setCountTime(format.format(parser.parse(String.valueOf(productMonitor.getCountTime()))));
            if (productMonitor.getType() == 1) {
                responseVo.setExpiringSum(productMonitor.getTotal());
            } else if (productMonitor.getType() == 2) {
                responseVo.setExpredSum(productMonitor.getTotal());
            }
        }
        return responseVo;
    }


    @Override
    public Pagination<ProductMonitorItemResponseVo> pageQuery(Integer type, String stationId, Integer version, int page, int pageSize) {
        // 根据station_id查询store_id，如果查询不到则忽略stationId
        Integer storeId = StringUtils.isNotBlank(stationId) ? getStoreIdByStationId(stationId) : null;
        // 页码参数
        page = Math.max(page, 1);
        pageSize = pageSize > Constants.MAX_PAGE_SIZE ? Constants.MAX_PAGE_SIZE : Math.max(pageSize, 1);
        // 分页结果封装
        Pagination<ProductMonitorItemResponseVo> pagination = new Pagination<>();
        pagination.setPage((long) page);
        pagination.setPageSize((long) pageSize);
        pagination.setTotalRecords(storeProductMonitorDao.countByTypeAndStoreId(type, storeId, version));
        Long totalPages = pagination.getTotalPages();
        if (totalPages == null || totalPages == 0 || page > totalPages) {
            pagination.setModels(Collections.emptyList());
        } else {
            int skip = (page - 1) * pageSize;
            List<StoreProductMonitor> productMonitors = storeProductMonitorDao.selectByTypeWithPage(type, storeId, version, skip, pageSize);
            List<Integer> productIds = productMonitors.stream()
                    .map(StoreProductMonitor::getProductId)
                    .distinct()
                    .collect(Collectors.toList());
            // 查询商品名称
            Map<Integer, String> productIdMap = productDao.selectProductNameByProductIds(productIds).stream()
                    .collect(Collectors.toMap(Product::getProductId, Product::getProductName));
            // Redis缓存里没有name的storeId
            Set<Integer> storeIds = new HashSet<>();
            List<ProductMonitorItemResponseVo> responseVos = new ArrayList<>();
            for (StoreProductMonitor productMonitor : productMonitors) {
                ProductMonitorItemResponseVo responseVo = new ProductMonitorItemResponseVo();
                responseVo.setProductId(productMonitor.getProductId());
                responseVo.setProductName(productIdMap.get(productMonitor.getProductId()));
                responseVo.setStoreId(productMonitor.getStoreId());
                String storeName = (String) redisCacheManager.getCache(CacheKeyConstants.STORE_NAME_KEY + responseVo.getStoreId());
                if (storeName != null) {
                    responseVo.setStoreName(storeName);
                } else {
                    storeIds.add(responseVo.getStoreId());
                }
                responseVo.setTotal(productMonitor.getTotal());
                responseVos.add(responseVo);
            }
            pagination.setModels(responseVos);
            if (storeIds.size() > 0) {
                List<Store> stores = storeDao.selectByIds(new ArrayList<>(storeIds));
                stores.forEach(store -> {
                    redisCacheManager.putCache(CacheKeyConstants.STORE_NAME_KEY + store.getStoreId(), store.getStoreName(), 3600);
                    responseVos.stream()
                            .filter(e -> StringUtils.isBlank(e.getStoreName()) && e.getStoreId().equals(store.getStoreId()))
                            .forEach(e -> e.setStoreName(store.getStoreName()));
                });
            }
        }
        return pagination;
    }

    /**
     * 根据买菜服务站ID查询storeId
     *
     * @param stationId
     * @return
     */
    private Integer getStoreIdByStationId(String stationId) {
        Store queryObj = new Store();
        queryObj.setStationId(stationId);
        Store resultObj = storeDao.selectOne(queryObj);
        return resultObj != null ? resultObj.getStoreId() : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteStoreProductMonitorWithOtherVersion(Integer version) {
        return storeProductMonitorDao.deleteStoreProductMonitorWithOtherVersion(version);
    }
}