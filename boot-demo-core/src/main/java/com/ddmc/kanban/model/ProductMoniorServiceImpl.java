package com.ddmc.kanban.model;

import com.ddmc.core.model.Pagination;
import com.ddmc.kanban.dao.*;
import com.ddmc.kanban.response.product.monitor.ProductMonitorItemResponseVo;
import com.ddmc.kanban.response.product.monitor.ProductMonitorSummaryResponseVo;
import com.ddmc.kanban.service.ProductMonitorService;
import com.ddmc.kanban.util.ProductUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
    private static final int DEFAULT_PAGE_SIZE = 1000;
    /**
     * 过期超过天数且库存为0的移到历史表，不再参与临期或过期商品统计
     */
    private static final int DEFAULT_DAY_OF_EXPIRED = 20;

    private final StoreProductBatchDao storeProductBatchDao;
    private final ProductDao productDao;
    private final StoreProductBatchTempDao storeProductBatchTempDao;
    private final StoreProductBatchHistoryDao storeProductBatchHistoryDao;
    private final ProductMonitorDao productMonitorDao;
    private final StoreProductBatchMonitorDao storeProductBatchMonitorDao;
    private final StoreProductMonitorDao storeProductMonitorDao;

    public ProductMoniorServiceImpl(StoreProductBatchDao storeProductBatchDao,
                                    ProductDao productDao, StoreProductBatchTempDao storeProductBatchTempDao,
                                    StoreProductBatchHistoryDao storeProductBatchHistoryDao, ProductMonitorDao productMonitorDao,
                                    StoreProductBatchMonitorDao storeProductBatchMonitorDao,
                                    StoreProductMonitorDao storeProductMonitorDao) {
        this.storeProductBatchDao = storeProductBatchDao;
        this.productDao = productDao;
        this.storeProductBatchTempDao = storeProductBatchTempDao;
        this.storeProductBatchHistoryDao = storeProductBatchHistoryDao;
        this.productMonitorDao = productMonitorDao;
        this.storeProductBatchMonitorDao = storeProductBatchMonitorDao;
        this.storeProductMonitorDao = storeProductMonitorDao;
    }

    /**
     * 把scm.store_product_batch表数据同步至kanban.store_product_batch_temp和kanban.store_product_batch_history
     */
    private void syncStoreProductBatch() throws Exception {
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
        // 筛选条件变化
        boolean conditionChanged = false;
        while (true) {
            conditionChanged = false;
            int skip = page * DEFAULT_PAGE_SIZE;
            List<StoreProductBatch> storeProductBatchList = storeProductBatchDao.selectWithPage(storeId, productId, batchId, skipDate, skip, DEFAULT_PAGE_SIZE);
            List<StoreProductBatchTemp> storeProductBatchTempList = new ArrayList<>();
            if (storeProductBatchList.size() < DEFAULT_PAGE_SIZE) {
                storeProductBatchTempList = storeProductBatchList.stream().map(e -> {
                    StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
                    BeanUtils.copyProperties(e, storeProductBatchTemp);
                    return new StoreProductBatchTemp();
                }).collect(Collectors.toList());
                doSomething(storeProductBatchTempList);
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
                    doSomething(storeProductBatchTempList);
                    continue;
                } else {
                    for (int i = 0; i <= index; i++) {
                        StoreProductBatchTemp storeProductBatchTemp = new StoreProductBatchTemp();
                        BeanUtils.copyProperties(storeProductBatchTemp, storeProductBatchTemp);
                        storeProductBatchTempList.add(storeProductBatchTemp);
                    }
                    doSomething(storeProductBatchTempList);
                    page = 0;
                    continue;
                }
            }
        }
        // TODO

    }

    private void doSomething (List<StoreProductBatchTemp> storeProductBatchTempList) {
        if (!storeProductBatchTempList.isEmpty()) {
            List<StoreProductBatchTemp> storeProductBatchTemps = new ArrayList<>();
            List<StoreProductBatchHistory> storeProductBatchHistories = new ArrayList<>();
            // 根据productIds查询商品信息，同时对商品进行过滤（不满足条件的商品排除掉）
            Map<Integer, ProductQualityAndSalePreiod> productQualityAndSalePreiodMap = productDao.selectPeriodsByProductIds(productIdSet).stream()
                    .collect(Collectors.toMap(ProductQualityAndSalePreiod::getProductId, Function.identity()));
            for (StoreProductBatchTemp storeProductBatchTemp : storeProductBatchTempList) {
                // 当前记录满足移到history表, 而且之前统计过，那么需要到temp将可能存在的记录删除
                // 考虑到商品信息可能发生变更，导致没有筛选到，所以在这里暴力删除一下，用到了索引，性能应该还可以接受
                if (lastCountTime != null) {
                    storeProductBatchTempDao.deleteStoreProductBatch(storeProductBatchTemp.getStoreId(), storeProductBatchTemp.getProductId(), storeProductBatchTemp.getBatchId());
                }
                ProductQualityAndSalePreiod productQualityAndSalePreiod = productQualityAndSalePreiodMap.get(storeProductBatchTemp.getProductId());
                // == null视为被过滤掉了
                if (productQualityAndSalePreiod != null) {
                    storeProductBatchTemp.setQualityPeriod(productQualityAndSalePreiod.getQualityPeriod());
                    storeProductBatchTemp.setSalePeriod(productQualityAndSalePreiod.getSalePeriod());
                    // 商品过期达到20天并且库存为0，移到history表
                    if (ProductUtil.getExpiredDay(storeProductBatchTemp.getBatchId(), storeProductBatchTemp.getQualityPeriod(), now) >= DEFAULT_DAY_OF_EXPIRED && storeProductBatchTemp.getAmount().doubleValue() <= 0d) {
                        StoreProductBatchHistory storeProductBatchHistory = new StoreProductBatchHistory();
                        BeanUtils.copyProperties(storeProductBatchTemp, storeProductBatchHistory);
                        storeProductBatchHistories.add(storeProductBatchHistory);
                    } else {
                        storeProductBatchTemps.add(storeProductBatchTemp);
                    }
                }
            }
            if (!storeProductBatchTemps.isEmpty()) {
                storeProductBatchTempDao.insertList(storeProductBatchTemps);
            }
            if (!storeProductBatchHistories.isEmpty()) {
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
    private void syncTemp2BatchMonitor(Date now) throws Exception {
        // 先清空商品监控详情表，后面再重新计算
        storeProductBatchMonitorDao.deleteAll();
        int page = 0;
        Long skipId = null;
        while (true) {
            int skip = page * DEFAULT_PAGE_SIZE;
            List<StoreProductBatchTemp> storeProductBatchTemps = storeProductBatchTempDao.selectWithPage(skipId, skip, DEFAULT_PAGE_SIZE);
            if (CollectionUtils.isNotEmpty(storeProductBatchTemps)) {
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
                        storeProductBatchMonitor.setType(isExpired ? 2 : 1); // 过期商品
                        storeProductBatchMonitor.setTotal(storeProductBatchTemp.getAmount().intValue()); // TODO 待调整
                        storeProductBatchMonitor.setBatchUpdateTime(storeProductBatchTemp.getBatchUpdateTime());
                        storeProductBatchMonitor.setCreateTime(new Date());
                        storeProductBatchMonitors.add(storeProductBatchMonitor);
                    }
                    if (i == storeProductBatchMonitors.size() - 1) {
                        skipId = storeProductBatchTemp.getId();
                    }
                }
                if (storeProductBatchMonitors.size() > 0) {
                    storeProductBatchMonitorDao.insertList(storeProductBatchMonitors);
                }
            } else {
                break;
            }
        }

        int skuSum = storeProductBatchTempDao.countSkuNum();
        // 先清空商品监控详情表，后面再重新计算
        storeProductMonitorDao.deleteAll();
        List<ProductMonitor> productMonitors = new ArrayList<>();
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        Stream.of(1, 2).forEach(type -> {
//            executorService.submit(() -> {
//                productMonitors.add(syncBatch2StoreMonitor(now, type, skuSum)) ;
//            });
//        });
//        try {
//            executorService.awaitTermination(3600L, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            logger.error("2线程统计过期或临期商品数目等待终止异常!", e);
//        }
        Stream.of(1, 2).forEach(type -> {
            productMonitors.add(syncBatch2StoreMonitor(now, type, skuSum));
        });
        productMonitors.forEach(productMonitor -> {
            productMonitor.setExeStartTime(new Date());
            productMonitor.setExeEndTime(new Date());
            productMonitor.setCountTime(0);
        });
        productMonitorDao.insertList(productMonitors);


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
            // 没有查到就说明全表扫描过了，终止
            if (storeProductMonitors == null || storeProductMonitors.isEmpty()) {
                break;
            }
            for (StoreProductMonitor storeProductMonitor : storeProductMonitors) {
                storeProductMonitor.setCreateTime(new Date());
                total += storeProductMonitor.getTotal();
            }
            storeProductMonitorDao.insertList(storeProductMonitors);
        }
        ProductMonitor productMonitor = new ProductMonitor();
        productMonitor.setType(type);
        productMonitor.setTotal(total);
        productMonitor.setSkuSum(skuSum);
        productMonitor.setCreateTime(new Date());
        return productMonitor;

    }


    @Override
    public ProductMonitorSummaryResponseVo getProductMonitorySummary() {
        ProductMonitorSummaryResponseVo responseVo = new ProductMonitorSummaryResponseVo();
        List<ProductMonitor> productMonitors = productMonitorDao.selectLastGroupByType();
        for (ProductMonitor productMonitor : productMonitors) {
            responseVo.setSkuSum(productMonitor.getSkuSum());
            responseVo.setCountTime(""); // TODO 待设置
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
            List<Integer> productIds = productMonitors.stream().map(StoreProductMonitor::getProductId).collect(Collectors.toList());
            Map<Integer, String> productIdMap = productDao.selectProductNameByProductIds(productIds).stream()
                    .collect(Collectors.toMap(Product::getProductId, Product::getProductName));
            List<ProductMonitorItemResponseVo> responseVos = new ArrayList<>();
            for (StoreProductMonitor productMonitor : productMonitors) {
                ProductMonitorItemResponseVo responseVo = new ProductMonitorItemResponseVo();
                responseVo.setProductId(productMonitor.getProductId());
                responseVo.setProductName(productIdMap.get(productMonitor.getProductId()));
                responseVo.setStoreId(productMonitor.getStoreId());
                responseVo.setStoreName("怎么搞?");
                responseVo.setTotal(productMonitor.getTotal());
                responseVos.add(responseVo);
            }
            pagination.setModels(responseVos);

        }
        return pagination;
    }

    @Override
    public void test() {
        try {
            this.syncStoreProductBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}