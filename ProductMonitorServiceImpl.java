package com.ddmc.kanban.service.impl;

import com.ddmc.core.model.Pagination;
import com.ddmc.kanban.constant.CacheKeyConstants;
import com.ddmc.kanban.dao.kanban.*;
import com.ddmc.kanban.model.*;
import com.ddmc.kanban.response.product.monitor.ProductMonitorItemResponseVo;
import com.ddmc.kanban.response.product.monitor.ProductMonitorSummaryResponseVo;
import com.ddmc.kanban.service.ProductMonitorService;
import com.ddmc.kanban.util.ProductUtil;
import com.ddmc.redis.ICacheManager;
import org.apache.commons.collections4.CollectionUtils;
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
@Transactional(rollbackFor = Exception.class)
public class ProductMonitorServiceImpl implements ProductMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(ProductMonitorServiceImpl.class);

    /**
     * 默认分页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 200;
    /**
     * 统计时间格式
     */
    private static final String COUNT_TIME_FORMART = "yyyyMMddHH";

    private final StoreProductBatchDao storeProductBatchDao;
    private final ProductDao productDao;
    private final StoreProductBatchTempDao storeProductBatchTempDao;
    private final ProductMonitorDao productMonitorDao;
    private final StoreProductMonitorDao storeProductMonitorDao;
    private final ICacheManager redisCacheManager;
    private final StoreDao storeDao;

    public ProductMonitorServiceImpl(StoreProductBatchDao storeProductBatchDao,
                                     ProductDao productDao, StoreProductBatchTempDao storeProductBatchTempDao,
                                     ProductMonitorDao productMonitorDao,
                                     StoreProductMonitorDao storeProductMonitorDao,
                                     ICacheManager redisCacheManager, StoreDao storeDao) {
        this.storeProductBatchDao = storeProductBatchDao;
        this.productDao = productDao;
        this.storeProductBatchTempDao = storeProductBatchTempDao;
        this.productMonitorDao = productMonitorDao;
        this.storeProductMonitorDao = storeProductMonitorDao;
        this.redisCacheManager = redisCacheManager;
        this.storeDao = storeDao;
    }

    /**
     * 把scm.store_product_batch表中临期过期记录抽取到kanban.store_product_batch_temp
     *
     * @return
     * @throws Exception
     */
    @Override
    public Date syncStoreProductBatch() throws Exception {
        Date now = new Date();
        Integer lastCountTime = productMonitorDao.selectMaxCountTime();
        Date skipDate = null;
        if (lastCountTime == null) {
            // 说明之前没有统计过，自然没有kanban.store_product_batch_temp
            storeProductBatchTempDao.deleteAll();
        } else {
            SimpleDateFormat format = new SimpleDateFormat(COUNT_TIME_FORMART);
            skipDate = format.parse(String.valueOf(lastCountTime));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(skipDate);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            skipDate = calendar.getTime();
        }
        Integer storeId = null;
        Integer productId = null;
        String batchId = null;
        int page = 0;
        while (true) {
            int skip = page * DEFAULT_PAGE_SIZE;
            // 筛选条件目前包括store_product_batch.amount>0，history暂时弃之不用
            List<StoreProductBatch> storeProductBatchList = storeProductBatchDao.selectWithPage(storeId, productId, batchId, skipDate, skip, DEFAULT_PAGE_SIZE + 1);
            if (storeProductBatchList.size() <= DEFAULT_PAGE_SIZE) {
                saveStoreProductBatch2Temp(storeProductBatchList, storeProductBatchList.size() - 1, lastCountTime, now);
                logger.info("分页查询store_product_batch记录，当页记录数没有达到pageSize+1，没有下一页了，终止! storeId: {}, productId: {}, batchId: {}, page: {}, record: {}: pageSize + 1: {}",
                        storeId, productId, batchId, page + 1, storeProductBatchList.size(), DEFAULT_PAGE_SIZE + 1);
                break;
            } else {
                logger.info("分页查询store_product_batch记录，当页记录数达到pageSize+1，还有下一页，继续! storeId: {}, productId: {}, batchId: {}, page: {}, record: {}: pageSize + 1: {}",
                        storeId, productId, batchId, page + 1, storeProductBatchList.size(), DEFAULT_PAGE_SIZE + 1);
                saveStoreProductBatch2Temp(storeProductBatchList, storeProductBatchList.size() - 2, lastCountTime, now);
                StoreProductBatch lastOne = storeProductBatchList.get(storeProductBatchList.size() - 1);
                storeId = lastOne.getStoreId();
                productId = lastOne.getProductId();
                batchId = lastOne.getBatchId();
            }
        }
        return now;
    }

    /**
     * 把store_product_batch根据条件分拣分别存到store_product_batch_temp和store_product_batch_history表
     *
     * @param storeProductBatchList 商品批次记录
     * @param lastCountTime         上次统计时间
     * @param now                   当前时间
     * @throws Exception
     */
    private void saveStoreProductBatch2Temp(List<StoreProductBatch> storeProductBatchList, int index, Integer lastCountTime, Date now) throws Exception {
        if (CollectionUtils.isEmpty(storeProductBatchList)) return;
        if (index >= storeProductBatchList.size()) index = storeProductBatchList.size() - 1;
        // 根据productIds查询商品信息，同时对商品进行过滤（不满足条件的商品排除掉）
        Set<Integer> productIdSet = storeProductBatchList.stream().map(StoreProductBatch::getProductId).collect(Collectors.toSet());
        Map<Integer, ProductQualityAndSalePeriod> productQualityAndSalePeriodMap = productDao.selectPeriodsByProductIds(productIdSet).stream()
                .collect(Collectors.toMap(ProductQualityAndSalePeriod::getProductId, Function.identity()));
        List<StoreProductBatchTemp> storeProductBatchTemps = new ArrayList<>();
        for (int i = 0; i <= index; i++) {
            StoreProductBatch storeProductBatch = storeProductBatchList.get(i);
            ProductQualityAndSalePeriod productQualityAndSalePeriod = productQualityAndSalePeriodMap.get(storeProductBatch.getProductId());
            // 商品过滤，==null说明商品不在临期过期商品统计范畴中
            if (productQualityAndSalePeriod == null) {
                continue;
            }
            // 判断商品是否过期临期：过期一定临期，不过期才需要判断临期
            boolean isExpired = ProductUtil.getExpiredDay(storeProductBatch.getBatchId(), productQualityAndSalePeriod.getQualityPeriod(), now) > 0;
            boolean isExpiring = isExpired || ProductUtil.isExpiring(storeProductBatch.getBatchId(), productQualityAndSalePeriod.getSalePeriod(), now);
            if (isExpired || isExpiring) {
                Integer type = isExpired ? 2 : 1;
                if (lastCountTime != null) {
                    StoreProductBatchTemp storeProductBatchTemp
                            = storeProductBatchTempDao.selectStoreProductBatch(storeProductBatch.getStoreId(), storeProductBatch.getProductId(), storeProductBatch.getBatchId());
                    if (storeProductBatchTemp == null) {
                        storeProductBatchTemps.add(generateStoreProductBatchTemp(storeProductBatch, productQualityAndSalePeriod, type));
                    } else {
                        // 如果商品保质期天数、可售期天数、商品批次更新时间、最新的临期过期类型发生了变化，则更新，反之跳过
                        if (!type.equals(storeProductBatchTemp.getType())
                                || !storeProductBatch.getUpdateTime().equals(storeProductBatchTemp.getBatchUpdateTime())
                                || !storeProductBatchTemp.getQualityPeriod().equals(productQualityAndSalePeriod.getQualityPeriod())
                                || !storeProductBatchTemp.getSalePeriod().equals(productQualityAndSalePeriod.getSalePeriod())) {
                            StoreProductBatchTemp updateObj = new StoreProductBatchTemp();
                            updateObj.setId(storeProductBatchTemp.getId());
                            updateObj.setType(type);
                            updateObj.setBatchUpdateTime(storeProductBatch.getUpdateTime());
                            updateObj.setQualityPeriod(productQualityAndSalePeriod.getQualityPeriod());
                            updateObj.setSalePeriod(productQualityAndSalePeriod.getSalePeriod());
                            updateObj.setUpdateTime(new Date());
                            storeProductBatchTempDao.update(updateObj);
                        }
                    }
                } else {
                    storeProductBatchTemps.add(generateStoreProductBatchTemp(storeProductBatch, productQualityAndSalePeriod, type));
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
     * @param type
     * @return
     */
    private StoreProductBatchTemp generateStoreProductBatchTemp(StoreProductBatch storeProductBatch, ProductQualityAndSalePeriod productQualityAndSalePeriod, Integer type) {
        StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
        storeProductBatchTemp.setType(type);
        storeProductBatchTemp.setStoreId(storeProductBatch.getStoreId());
        storeProductBatchTemp.setProductId(storeProductBatch.getProductId());
        storeProductBatchTemp.setBatchId(storeProductBatch.getBatchId());
        storeProductBatchTemp.setAmount(storeProductBatch.getAmount());
        storeProductBatchTemp.setQualityPeriod(productQualityAndSalePeriod.getQualityPeriod());
        storeProductBatchTemp.setSalePeriod(productQualityAndSalePeriod.getSalePeriod());
        storeProductBatchTemp.setBatchUpdateTime(storeProductBatch.getUpdateTime());
        storeProductBatchTemp.setCreateTime(new Date());
        return storeProductBatchTemp;
    }

    /**
     * kanban.store_product_batch_temp中临期过期商品批次记录抽取门店过期临期商品数，其实就是批次合并
     *
     * @param time
     */
    @Override
    public void syncStoreProductBatchTemp2StoreProductMonitor(Date time) {
        int skuSum = storeProductBatchTempDao.countSkuNum();
        // 先清空商品监控详情表，后面再重新计算
        storeProductMonitorDao.deleteAll();
        List<Integer> types = Arrays.asList(1, 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        types.forEach(type -> {
            int total = 0;
            int page = 0;
            Integer storeId = null;
            Integer productId = null;
            while (true) {
                int skip = page * DEFAULT_PAGE_SIZE;
                List<StoreProductMonitor> storeProductMonitors = storeProductBatchTempDao.selectGroupByStoreProductWithPage(storeId, productId, type, skip, DEFAULT_PAGE_SIZE + 1);
                if (storeProductMonitors.size() <= DEFAULT_PAGE_SIZE) {
                    total += generateProductMonitor(storeProductMonitors, storeProductMonitors.size() - 1, type);
                    break;
                } else {
                    total += generateProductMonitor(storeProductMonitors, storeProductMonitors.size() - 2, type);
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
            productMonitor.setCountTime(Integer.valueOf(format.format(time)));
            productMonitorDao.insert(productMonitor);
        });
    }

    private int generateProductMonitor(List<StoreProductMonitor> storeProductMonitors, int index, Integer type) {
        if (index >= storeProductMonitors.size()) {
            index = storeProductMonitors.size() - 1;
        }
        int total = 0;
        for (int i = 0; i <= index; i++) {
            StoreProductMonitor storeProductMonitor = storeProductMonitors.get(i);
            storeProductMonitor.setType(type);
            storeProductMonitor.setCreateTime(new Date());
            total += storeProductMonitor.getTotal();
            storeProductMonitorDao.insert(storeProductMonitor);
        }
//        storeProductMonitorDao.insertList(storeProductMonitors.subList(0, index + 1));
        return total;
    }

    @Override
    public ProductMonitorSummaryResponseVo getProductMonitorySummary() throws Exception {
        ProductMonitorSummaryResponseVo responseVo = new ProductMonitorSummaryResponseVo();
        List<ProductMonitor> productMonitors = productMonitorDao.selectLastGroupByType();
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
    public Pagination<ProductMonitorItemResponseVo> listByType(Integer type, int page, int pageSize) {
        page = Math.max(page, 1);
        pageSize = Math.max(pageSize, 1);

        Pagination<ProductMonitorItemResponseVo> pagination = new Pagination<>();
        pagination.setPage((long) page);
        pagination.setPageSize((long) pageSize);
        pagination.setTotalRecords(storeProductMonitorDao.countByType(type));
        Long totalPages = pagination.getTotalPages();
        if (totalPages == null || totalPages == 0 || page > totalPages) {
            pagination.setModels(Collections.emptyList());
        } else {
            int skip = (page - 1) * pageSize;
            List<StoreProductMonitor> productMonitors = storeProductMonitorDao.selectByTypeWithPage(type, skip, pageSize);
            List<Integer> productIds = new ArrayList<>();
            productMonitors.forEach(monitor -> {
                productIds.add(monitor.getProductId());
            });
            // 查询商品名称
            Map<Integer, String> productIdMap = productDao.selectProductNameByProductIds(productIds).stream()
                    .collect(Collectors.toMap(Product::getProductId, Product::getProductName));
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
                // 查询storeName，放到缓存里
                List<Store> stores = storeDao.selectByIds(new ArrayList<>(storeIds));
                stores.forEach(store -> {
                    redisCacheManager.putCache(CacheKeyConstants.STORE_NAME_KEY + store.getStoreId(), store.getStoreName(), 3600);
                    responseVos.stream().filter(e -> StringUtils.isBlank(e.getStoreName())).forEach(element -> {
                        element.setStoreName(store.getStoreName());
                    });
                });
            }
        }
        return pagination;
    }

    @Override
    public void test() {
        StoreProductMonitor monitor1 = new StoreProductMonitor();
        monitor1.setType(1);
        monitor1.setTotal(1);
        monitor1.setStoreId(1);
        monitor1.setProductId(50);
        monitor1.setCreateTime(new Date());

        StoreProductMonitor monitor2 = new StoreProductMonitor();
        monitor2.setType(2);
        monitor2.setTotal(2);
        monitor2.setStoreId(1);
        monitor2.setProductId(50);
        monitor2.setCreateTime(new Date());
        storeProductMonitorDao.insertList(Arrays.asList(monitor1, monitor2));

    }
}