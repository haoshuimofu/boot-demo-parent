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
    private final StoreProductBatchHistoryDao storeProductBatchHistoryDao;
    private final ProductMonitorDao productMonitorDao;
    private final StoreProductBatchMonitorDao storeProductBatchMonitorDao;
    private final StoreProductMonitorDao storeProductMonitorDao;
    private final ICacheManager redisCacheManager;
    private final StoreDao storeDao;

    public ProductMonitorServiceImpl(StoreProductBatchDao storeProductBatchDao,
                                     ProductDao productDao, StoreProductBatchTempDao storeProductBatchTempDao,
                                     StoreProductBatchHistoryDao storeProductBatchHistoryDao, ProductMonitorDao productMonitorDao,
                                     StoreProductBatchMonitorDao storeProductBatchMonitorDao,
                                     StoreProductMonitorDao storeProductMonitorDao,
                                     ICacheManager redisCacheManager, StoreDao storeDao) {
        this.storeProductBatchDao = storeProductBatchDao;
        this.productDao = productDao;
        this.storeProductBatchTempDao = storeProductBatchTempDao;
        this.storeProductBatchHistoryDao = storeProductBatchHistoryDao;
        this.productMonitorDao = productMonitorDao;
        this.storeProductBatchMonitorDao = storeProductBatchMonitorDao;
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
            // 说明之前没有统计过，自然没有kanban.store_product_batch_temp和kanban.store_product_batch_history
            storeProductBatchTempDao.deleteAll();
            storeProductBatchHistoryDao.deleteAll();
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
            List<StoreProductBatch> storeProductBatchList = storeProductBatchDao.selectWithPage(storeId, productId, batchId, skipDate, skip, DEFAULT_PAGE_SIZE);
            if (storeProductBatchList.size() < DEFAULT_PAGE_SIZE) {
                saveStoreProductBatch2Temp(storeProductBatchList, storeProductBatchList.size(), lastCountTime, now);
                logger.info("分页查询store_product_batch记录，当页记录没有达到pageSize，终止! storeId: {}, productId: {}, batchId: {}, page: {}, record: {}",
                        storeId, productId, batchId, page + 1, storeProductBatchList.size());
                break;
            } else {
                StoreProductBatch lastOne = storeProductBatchList.get(storeProductBatchList.size() - 1);
                storeId = lastOne.getStoreId();
                productId = lastOne.getProductId();
                batchId = lastOne.getBatchId();
                int index = -1;
                for (int i = storeProductBatchList.size() - 2; i >= 0; i--) {
                    StoreProductBatch storeProductBatch = storeProductBatchList.get(i);
                    // store_id,product_id,batch_id有一个参数发生变化，则按新条件重新进入下一轮查询
                    if (!batchId.equals(storeProductBatch.getBatchId()) || !productId.equals(storeProductBatch.getProductId()) || !storeId.equals(storeProductBatch.getStoreId())) {
                        index = i;
                        break;
                    }
                }
                if (index < 0) {
                    page++;
                    index = storeProductBatchList.size();
                    logger.info("分页查询store_product_batch记录，当页条件没有变更，继续查询下一页记录! storeId: {}, productId: {}, batchId: {}, page: {}, nextPage: {}->{}",
                            storeId, productId, batchId, page, page + 1);
                } else {
                    page = 0;
                    logger.info("分页查询store_product_batch记录，当页条件有变更，使用新条件从第一页开始查询! storeId: {}, productId: {}, batchId: {}, page: {}",
                            storeId, productId, batchId, page + 1);
                }
                saveStoreProductBatch2Temp(storeProductBatchList, index, lastCountTime, now);
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
        List<StoreProductBatchTemp> storeProductBatchTemps = new ArrayList<>();
        List<StoreProductBatchHistory> storeProductBatchHistories = new ArrayList<>();
        // 根据productIds查询商品信息，同时对商品进行过滤（不满足条件的商品排除掉）
        Set<Integer> productIdSet = storeProductBatchList.stream().map(StoreProductBatch::getProductId).collect(Collectors.toSet());
        Map<Integer, ProductQualityAndSalePreiod> productQualityAndSalePreiodMap = productDao.selectPeriodsByProductIds(productIdSet).stream()
                .collect(Collectors.toMap(ProductQualityAndSalePreiod::getProductId, Function.identity()));
        for (int i = 0; i <= index; i++) {
            StoreProductBatch storeProductBatch = storeProductBatchList.get(i);
            // 当前记录满足移到history表, 而且之前统计过，那么需要到temp将可能存在的记录删除
            // 考虑到商品信息可能发生变更，导致没有筛选到，所以在这里暴力删除一下，用到了索引，性能应该还可以接受
            if (lastCountTime != null) {
                storeProductBatchTempDao.deleteStoreProductBatch(storeProductBatch.getStoreId(), storeProductBatch.getProductId(), storeProductBatch.getBatchId());
            }
            ProductQualityAndSalePreiod productQualityAndSalePreiod = productQualityAndSalePreiodMap.get(storeProductBatch.getProductId());
            // == null视为被过滤掉了
            if (productQualityAndSalePreiod != null) {
                // 商品过期达到20天并且库存为0，移到history表
                if (ProductUtil.getExpiredDay(storeProductBatch.getBatchId(), productQualityAndSalePreiod.getQualityPeriod(), now) >= Constants.EXPIRED_DAY_TO_HISTORY
                        && storeProductBatch.getAmount().doubleValue() == 0d) {
                    StoreProductBatchHistory storeProductBatchHistory = new StoreProductBatchHistory();
                    storeProductBatchHistory.setStoreId(storeProductBatch.getStoreId());
                    storeProductBatchHistory.setProductId(storeProductBatch.getProductId());
                    storeProductBatchHistory.setBatchId(storeProductBatch.getBatchId());
                    storeProductBatchHistory.setAmount(storeProductBatch.getAmount());
                    storeProductBatchHistory.setQualityPeriod(productQualityAndSalePreiod.getQualityPeriod());
                    storeProductBatchHistory.setSalePeriod(productQualityAndSalePreiod.getSalePeriod());
                    // 商品批次最近更新时间
                    storeProductBatchHistory.setBatchUpdateTime(storeProductBatch.getUpdateTime());
                    storeProductBatchHistory.setCreateTime(new Date());
                    storeProductBatchHistories.add(storeProductBatchHistory);
                } else if (storeProductBatch.getAmount().doubleValue() > 0d) {
                    // 不满足移到history表，且有库存的才需要在临期或过期商品统计中
                    StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
                    storeProductBatchTemp.setStoreId(storeProductBatch.getStoreId());
                    storeProductBatchTemp.setProductId(storeProductBatch.getProductId());
                    storeProductBatchTemp.setBatchId(storeProductBatch.getBatchId());
                    storeProductBatchTemp.setAmount(storeProductBatch.getAmount());
                    storeProductBatchTemp.setQualityPeriod(productQualityAndSalePreiod.getQualityPeriod());
                    storeProductBatchTemp.setSalePeriod(productQualityAndSalePreiod.getSalePeriod());
                    // 商品批次最近更新时间
                    storeProductBatchTemp.setBatchUpdateTime(storeProductBatch.getUpdateTime());
                    storeProductBatchTemp.setCreateTime(new Date());
                    storeProductBatchTemps.add(storeProductBatchTemp);
                }
            }
        }
        if (!storeProductBatchTemps.isEmpty()) {
            logger.info("批量追加store_product_batch_temp记录: " + storeProductBatchTemps.size());
            storeProductBatchTempDao.insertList(storeProductBatchTemps);
        }
        if (!storeProductBatchHistories.isEmpty()) {
//            logger.info("批量追加store_product_batch_history记录: " + storeProductBatchHistories.size());
//            storeProductBatchHistoryDao.insertList(storeProductBatchHistories);
        }
    }

    /**
     * kanban.store_product_batch_temp中临期过期商品批次记录抽取门店过期临期商品数，其实就是批次合并
     *
     * @param time
     */
    @Override
    public void syncStoreProductBatchTemp2StoreProductMonitor(Date time) {

    }

    /**
     * 把store_product_batch_temp表中的过期或临期的商品批次数据抽取到store_product_batch_monitor表中
     *
     * @param now 统计开始时间
     * @throws Exception
     */
    @Override
    public void syncTemp2BatchMonitor(Date now) throws Exception {
        // 先清空商品监控详情表，后面再重新计算
        storeProductBatchMonitorDao.deleteAll();
        int page = 0;
        Long skipId = null;
        while (true) {
            int skip = page * DEFAULT_PAGE_SIZE;
            List<StoreProductBatchTemp> storeProductBatchTemps = storeProductBatchTempDao.selectWithPage(skipId, skip, DEFAULT_PAGE_SIZE);
            List<StoreProductBatchMonitor> storeProductBatchMonitors = new ArrayList<>();
            for (int i = 0; i < storeProductBatchTemps.size(); i++) {
                StoreProductBatchTemp storeProductBatchTemp = storeProductBatchTemps.get(i);
                boolean isExpired = ProductUtil.getExpiredDay(storeProductBatchTemp.getBatchId(), storeProductBatchTemp.getQualityPeriod(), now) > 0;
                // 如果是过期的，那直接认定为已经临期了，否则再判断是否真的临期了
                boolean isExpiring = isExpired || ProductUtil.isExpiring(storeProductBatchTemp.getBatchId(), storeProductBatchTemp.getSalePeriod(), now);
                if (isExpired || isExpiring) {
                    StoreProductBatchMonitor batchMonitor
                            = storeProductBatchMonitorDao.selectStoreProductBatchMonitor(storeProductBatchTemp.getStoreId(), storeProductBatchTemp.getProductId(), storeProductBatchTemp.getBatchId());
                    // 如果记录存在且batchUpdateTime的更新时间没有变化则跳过
                    if (batchMonitor != null) {
                        if (storeProductBatchTemp.getBatchUpdateTime().equals(batchMonitor.getBatchUpdateTime())) {
                            continue;
                        } else {
                            // 这里根据id直接删除，后面再新增
                            storeProductBatchMonitorDao.deleteById(batchMonitor.getId());
                        }
                    }
                    StoreProductBatchMonitor storeProductBatchMonitor = new StoreProductBatchMonitor();
                    storeProductBatchMonitor.setType(isExpired ? 2 : 1);
                    storeProductBatchMonitor.setStoreId(storeProductBatchTemp.getStoreId());
                    storeProductBatchMonitor.setProductId(storeProductBatchTemp.getProductId());
                    storeProductBatchMonitor.setBatchId(storeProductBatchTemp.getBatchId());
                    storeProductBatchMonitor.setBatchUpdateTime(storeProductBatchTemp.getBatchUpdateTime());
                    storeProductBatchMonitor.setQualityPeriod(storeProductBatchTemp.getQualityPeriod());
                    storeProductBatchMonitor.setSalePeriod(storeProductBatchTemp.getSalePeriod());
                    storeProductBatchMonitor.setAmount(storeProductBatchTemp.getAmount());
                    storeProductBatchMonitor.setCreateTime(new Date());
                    storeProductBatchMonitors.add(storeProductBatchMonitor);
                }
                if (i == storeProductBatchTemps.size() - 1) {
                    skipId = storeProductBatchTemp.getId();
                }
            }
            if (storeProductBatchMonitors.size() > 0) {
                logger.info("批量插入store_product_batch_monitor记录: {}", storeProductBatchMonitors.size());
                storeProductBatchMonitorDao.insertList(storeProductBatchMonitors);
            }
            if (storeProductBatchTemps.size() < DEFAULT_PAGE_SIZE) {
                logger.info("批量插入store_product_batch_monitor, 从max(id): {}开始不满一页, size: {}，终止!", skipId, storeProductBatchMonitors.size());
                break;
            }
        }
    }

    /**
     * 把store_product_batch_monitor表中数据group抽取到store_product_monitor
     *
     * @param now
     */
    @Override
    public void syncBatch2StoreMonitor(Date now) {
        int skuSum = storeProductBatchTempDao.countSkuNum();
        // 先清空商品监控详情表，后面再重新计算
        storeProductMonitorDao.deleteAll();
        List<Integer> types = Arrays.asList(1, 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        types.forEach(type -> {
            int total = 0;
            int page = 0;
            while (true) {
                int skip = page++ * DEFAULT_PAGE_SIZE;
                List<StoreProductMonitor> storeProductMonitors = storeProductBatchMonitorDao.selectGroupByStoreProductWithPage(type, skip, DEFAULT_PAGE_SIZE);
                for (StoreProductMonitor storeProductMonitor : storeProductMonitors) {
                    storeProductMonitor.setCreateTime(new Date());
                    total += storeProductMonitor.getTotal();
                }
//                storeProductMonitorDao.insertList(storeProductMonitors);
                for (StoreProductMonitor storeProductMonitor : storeProductMonitors) {
                    storeProductMonitorDao.insert(storeProductMonitor);
                }
                // 没有满页终止
                if (storeProductMonitors.size() < DEFAULT_PAGE_SIZE) {
                    break;
                }
            }
            ProductMonitor productMonitor = new ProductMonitor();
            productMonitor.setType(type);
            productMonitor.setTotal(total);
            productMonitor.setSkuSum(skuSum);
            productMonitor.setCreateTime(new Date());
            productMonitor.setExeStartTime(now);
            productMonitor.setExeEndTime(new Date());
            productMonitor.setCountTime(Integer.valueOf(format.format(now)));
            productMonitorDao.insert(productMonitor);
        });
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