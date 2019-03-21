package com.ddmc.kanban.service.impl;

import com.ddmc.core.model.Pagination;
import com.ddmc.kanban.constant.CacheKeyConstants;
import com.ddmc.kanban.constant.Constants;
import com.ddmc.kanban.dao.mybatis.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 商品过期临期监控
 *
 * @Author wude
 * @Create 2019-03-18 17:34
 */
@Service
public class ProductMoniorServiceImpl implements ProductMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(ProductMoniorServiceImpl.class);

    /**
     * 默认分页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 200;

    private final StoreProductBatchDao storeProductBatchDao;
    private final ProductDao productDao;
    private final StoreProductBatchTempDao storeProductBatchTempDao;
    private final StoreProductBatchHistoryDao storeProductBatchHistoryDao;
    private final ProductMonitorDao productMonitorDao;
    private final StoreProductBatchMonitorDao storeProductBatchMonitorDao;
    private final StoreProductMonitorDao storeProductMonitorDao;
    private final DataSourceTransactionManager kanbanTransactionManager;
    private final ICacheManager redisCacheManager;
    private final StoreDao storeDao;

    public ProductMoniorServiceImpl(StoreProductBatchDao storeProductBatchDao,
                                    ProductDao productDao, StoreProductBatchTempDao storeProductBatchTempDao,
                                    StoreProductBatchHistoryDao storeProductBatchHistoryDao, ProductMonitorDao productMonitorDao,
                                    StoreProductBatchMonitorDao storeProductBatchMonitorDao,
                                    StoreProductMonitorDao storeProductMonitorDao,
                                    @Qualifier("kanbanTransactionManager") DataSourceTransactionManager kanbanTransactionManager,
                                    ICacheManager redisCacheManager, StoreDao storeDao) {
        this.storeProductBatchDao = storeProductBatchDao;
        this.productDao = productDao;
        this.storeProductBatchTempDao = storeProductBatchTempDao;
        this.storeProductBatchHistoryDao = storeProductBatchHistoryDao;
        this.productMonitorDao = productMonitorDao;
        this.storeProductBatchMonitorDao = storeProductBatchMonitorDao;
        this.storeProductMonitorDao = storeProductMonitorDao;
        this.kanbanTransactionManager = kanbanTransactionManager;
        this.redisCacheManager = redisCacheManager;
        this.storeDao = storeDao;
    }

    /**
     * 把scm.store_product_batch表数据同步至kanban.store_product_batch_temp和kanban.store_product_batch_history
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Date syncStoreProductBatch() throws Exception {
        Date now = new Date();
        Integer lastCountTime = productMonitorDao.selectMaxCountTime();
        Date skipDate = null;
        if (lastCountTime == null) {
            // 说明之前没有统计过，自然没有kanban.store_product_batch_temp和kanban.store_product_batch_history
            storeProductBatchTempDao.deleteAll();
            storeProductBatchHistoryDao.deleteAll();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
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
            List<StoreProductBatch> storeProductBatchList = storeProductBatchDao.selectWithPage(storeId, productId, batchId, skipDate, skip, DEFAULT_PAGE_SIZE);
            List<StoreProductBatchTemp> storeProductBatchTempList = new ArrayList<>();
            if (storeProductBatchList.size() < DEFAULT_PAGE_SIZE) {
                storeProductBatchTempList = storeProductBatchList.stream().map(e -> {
                    StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
                    BeanUtils.copyProperties(e, storeProductBatchTemp);
                    return new StoreProductBatchTemp();
                }).collect(Collectors.toList());
                saveStoreProductBatch2Temp(storeProductBatchTempList, lastCountTime, now);
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
                    storeProductBatchTempList = storeProductBatchList.stream().map(e -> {
                        StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
                        BeanUtils.copyProperties(e, storeProductBatchTemp);
                        return new StoreProductBatchTemp();
                    }).collect(Collectors.toList());
                    logger.info("分页查询store_product_batch记录，当页条件没有变更，继续查询下一页记录! storeId: {}, productId: {}, batchId: {}, page: {}, nextPage: {}->{}",
                            storeId, productId, batchId, page, page + 1);
                } else {
                    for (int i = 0; i <= index; i++) {
                        StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
                        BeanUtils.copyProperties(storeProductBatchList.get(i), storeProductBatchTemp);
                        storeProductBatchTempList.add(storeProductBatchTemp);
                    }
                    page = 0;
                    logger.info("分页查询store_product_batch记录，当页条件有变更，使用新条件从第一页开始查询! storeId: {}, productId: {}, batchId: {}, page: {}",
                            storeId, productId, batchId, page + 1);
                }
                saveStoreProductBatch2Temp(storeProductBatchTempList, lastCountTime, now);
            }
        }
        // TODO scm.store_product_batch表数据已经分拣到kanban.store_product_batch_temp和kanban.store_product_batch_history, 接着做？
        // this.syncTemp2BatchMonitor(now);
        return now;
    }

    /**
     * 把store_product_batch_temp记录分拣分别存到store_product_batch_temp和store_product_batch_history表
     *
     * @param storeProductBatchTempList 中间表记录
     * @param lastCountTime             上次统计时间
     * @param now                       当前时间
     * @throws Exception
     */
    private void saveStoreProductBatch2Temp(List<StoreProductBatchTemp> storeProductBatchTempList, Integer lastCountTime, Date now) throws Exception {
        if (CollectionUtils.isNotEmpty(storeProductBatchTempList)) {
            Set<Integer> productIdSet = storeProductBatchTempList.stream().map(StoreProductBatchTemp::getProductId).collect(Collectors.toSet());
            // 根据productIds查询商品信息，同时对商品进行过滤（不满足条件的商品排除掉）
            Map<Integer, ProductQualityAndSalePreiod> productQualityAndSalePreiodMap = productDao.selectPeriodsByProductIds(productIdSet).stream()
                    .collect(Collectors.toMap(ProductQualityAndSalePreiod::getProductId, Function.identity()));
            List<StoreProductBatchTemp> storeProductBatchTemps = new ArrayList<>();
            List<StoreProductBatchHistory> storeProductBatchHistories = new ArrayList<>();
            for (StoreProductBatchTemp storeProductBatchTemp : storeProductBatchTempList) {
                // 当前记录满足移到history表, 而且之前统计过，那么需要到temp将可能存在的记录删除
                // 考虑到商品信息可能发生变更，导致没有筛选到，所以在这里暴力删除一下，用到了索引，性能应该还可以接受
                if (lastCountTime != null) {
                    storeProductBatchTempDao.deleteStoreProductBatch(storeProductBatchTemp.getStoreId(), storeProductBatchTemp.getProductId(), storeProductBatchTemp.getBatchId());
                }
                ProductQualityAndSalePreiod productQualityAndSalePreiod = productQualityAndSalePreiodMap.get(storeProductBatchTemp.getProductId());
                // == null视为被过滤掉了
                if (productQualityAndSalePreiod != null) {
                    // 商品BeanUtil.copy，scm.store_product_batch.update_time->kanban.store_product_batch_temp.update_time
                    storeProductBatchTemp.setBatchUpdateTime(storeProductBatchTemp.getUpdateTime());
                    storeProductBatchTemp.setUpdateTime(null);
                    // 商品保质期和可售期，判断临期和过期
                    storeProductBatchTemp.setQualityPeriod(productQualityAndSalePreiod.getQualityPeriod());
                    storeProductBatchTemp.setSalePeriod(productQualityAndSalePreiod.getSalePeriod());
                    // 商品过期达到20天并且库存为0，移到history表
                    if (ProductUtil.getExpiredDay(storeProductBatchTemp.getBatchId(), storeProductBatchTemp.getQualityPeriod(), now) >= Constants.EXPIRED_DAY_TO_HISTORY
                            && storeProductBatchTemp.getAmount().doubleValue() <= 0d) {
                        StoreProductBatchHistory storeProductBatchHistory = new StoreProductBatchHistory();
                        BeanUtils.copyProperties(storeProductBatchTemp, storeProductBatchHistory);
                        storeProductBatchHistories.add(storeProductBatchHistory);
                    } else {
                        storeProductBatchTemps.add(storeProductBatchTemp);
                    }
                }
            }
            if (!storeProductBatchTemps.isEmpty()) {
                logger.info("批量追加store_product_batch_temp记录: " + storeProductBatchTemps.size());
                storeProductBatchTempDao.insertList(storeProductBatchTemps);
            }
            if (!storeProductBatchHistories.isEmpty()) {
                logger.info("批量追加store_product_batch_history记录: " + storeProductBatchHistories.size());
                storeProductBatchHistoryDao.insertList(storeProductBatchHistories);
            }
        }
    }

    /**
     * 把store_product_batch_temp表中临期或过期的记录抽取到store_product_batch_monitor表中，表分类临期还是过期
     *
     * @param now
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
                boolean isExpiring = ProductUtil.isExpiring(storeProductBatchTemp.getBatchId(), storeProductBatchTemp.getSalePeriod(), now);
                // 临期或过期，且有库存
                if (storeProductBatchTemp.getAmount().doubleValue() > 0d && (isExpired || isExpiring)) {
                    StoreProductBatchMonitor storeProductBatchMonitor = new StoreProductBatchMonitor();
                    storeProductBatchMonitor.setStoreId(storeProductBatchTemp.getStoreId());
                    storeProductBatchMonitor.setProductId(storeProductBatchTemp.getProductId());
                    storeProductBatchMonitor.setBatchId(storeProductBatchTemp.getBatchId());
                    storeProductBatchMonitor.setType(isExpired ? 2 : 1);
                    // TODO 库存值整数不合适
                    storeProductBatchMonitor.setTotal(storeProductBatchTemp.getAmount().intValue());
                    storeProductBatchMonitor.setBatchUpdateTime(storeProductBatchTemp.getBatchUpdateTime());
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

        int skuSum = storeProductBatchTempDao.countSkuNum();
        // 先清空商品监控详情表，后面再重新计算
        storeProductMonitorDao.deleteAll();
        List<ProductMonitor> productMonitors = new ArrayList<>();
        Stream.of(1, 2).forEach(type -> productMonitors.add(syncBatch2StoreMonitor(now, type, skuSum)));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        productMonitors.forEach(productMonitor -> {
            productMonitor.setExeStartTime(now);
            productMonitor.setExeEndTime(new Date());
            productMonitor.setCountTime(Integer.valueOf(format.format(now)));
        });
        int count = productMonitorDao.insertList(productMonitors);
    }

    /**
     * 把store_product_batch_monitor表中数据group抽取到store_product_monitor
     *
     * @param now    时间
     * @param type   临期或过期
     * @param skuSum SKU种类数
     */
    private ProductMonitor syncBatch2StoreMonitor(Date now, Integer type, Integer skuSum) {
        int total = 0;
        int page = 0;
        while (true) {
            int skip = page++ * DEFAULT_PAGE_SIZE;
            List<StoreProductMonitor> storeProductMonitors = storeProductBatchMonitorDao.selectGroupByStoreProdctWithPage(type, skip, DEFAULT_PAGE_SIZE);
            for (StoreProductMonitor storeProductMonitor : storeProductMonitors) {
                storeProductMonitor.setCreateTime(new Date());
                total += storeProductMonitor.getTotal();
            }
//            storeProductMonitorDao.insertList(storeProductMonitors);
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
        return productMonitor;

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
}