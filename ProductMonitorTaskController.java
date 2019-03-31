package com.ddmc.kanban.controller.manage;

import com.ddmc.core.view.ResponseBaseVo;
import com.ddmc.kanban.constant.Constants;
import com.ddmc.kanban.constant.ErrorCodeConstants;
import com.ddmc.kanban.model.StoreProductBatch;
import com.ddmc.kanban.model.StoreProductBatchJoinProductBean;
import com.ddmc.kanban.service.ProductMonitorService;
import com.ddmc.kanban.service.StoreService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品监控任务控制器
 *
 * @Author wude
 * @Create 2019-03-25 16:58
 */
@RestController
@RequestMapping(value = "manage/product/monitor")
public class ProductMonitorTaskController {

    private static final Logger logger = LoggerFactory.getLogger(ProductMonitorTaskController.class);

    @Value("${product.monitor.city.id}")
    private String cityId;

    /**
     * 统计时间格式
     */
    private static final String COUNT_TIME_FORMART = "yyyyMMddHH";

    private final ProductMonitorService productMonitorService;
    private final StoreService storeService;

    public ProductMonitorTaskController(ProductMonitorService productMonitorService, StoreService storeService) {
        this.productMonitorService = productMonitorService;
        this.storeService = storeService;
    }

    /**
     * 刷新临期过期商品监控数据
     *
     * @return
     */
    @GetMapping(value = "refresh")
    public ResponseBaseVo refresh() {
        Date now = new Date();
        // 互斥锁
        if (!productMonitorService.tryLockProductMonitor(now)) {
            return ResponseBaseVo.fail(ErrorCodeConstants.ERROR_CODE_801.getErrorCode(), ErrorCodeConstants.ERROR_CODE_801.getErrorMsg());
        }
        try {
            long start = now.getTime();
            Map<Integer, Integer> storeIdMap = storeService.getCitySaleStoreIds(cityId).stream().collect(Collectors.toMap(e -> e, e -> e));
            List<String> logs = new ArrayList<>();
            Integer lastCountTime = productMonitorService.getLastCountTime();
            int page = 0;
            int pageSize = Constants.PRODUCT_MONITOR_TASK_PAGE_SIZE;
            int skip = page * pageSize;
            Integer storeId = null;
            Integer productId = null;
            String batchId = null;
            if (lastCountTime == null) {
                // 第一次统计尝试清空kanban.store_product_batch_temp表
                productMonitorService.clearStoreProductBatchTemp();
                while (true) {
                    // 第一次统计筛选条件：store_product_batch.batch_id != '000000' and amount > 0
                    List<StoreProductBatch> storeProductBatchList = productMonitorService.selectStoreProductBatchWithPage(storeId, productId, batchId, skip, pageSize + 1);
                    if (CollectionUtils.isEmpty(storeProductBatchList)) break;
                    if (storeProductBatchList.size() <= pageSize) {
                        productMonitorService.parseAndSaveStoreProductBatch2Temp(storeProductBatchList, storeProductBatchList.size() - 1, now, storeIdMap);
                        logger.info("分页查询store_product_batch记录，当页记录数没有达到pageSize+1，没有下一页了，终止! storeId: {}, productId: {}, batchId: {}, page: {}, record: {}: pageSize + 1: {}",
                                storeId, productId, batchId, page + 1, storeProductBatchList.size(), pageSize + 1);
                        break;
                    } else {
                        logger.info("分页查询store_product_batch记录，当页记录数达到pageSize+1，还有下一页，继续! storeId: {}, productId: {}, batchId: {}, page: {}, record: {}: pageSize + 1: {}",
                                storeId, productId, batchId, page + 1, storeProductBatchList.size(), pageSize + 1);
                        productMonitorService.parseAndSaveStoreProductBatch2Temp(storeProductBatchList, storeProductBatchList.size() - 2, now, storeIdMap);
                        StoreProductBatch lastOne = storeProductBatchList.get(storeProductBatchList.size() - 1);
                        storeId = lastOne.getStoreId();
                        productId = lastOne.getProductId();
                        batchId = lastOne.getBatchId();
                    }
                }
            } else {
                SimpleDateFormat format = new SimpleDateFormat(COUNT_TIME_FORMART);
                Date skipDate = format.parse(String.valueOf(lastCountTime));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(skipDate);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                skipDate = calendar.getTime();
                while (true) {
                    // 不是第一次统计，条件中就不能带amount>0了，因为之前可能amount>0
                    List<StoreProductBatchJoinProductBean> storeProductBatchJoinProductBeanList = productMonitorService.selectStoreProductBatchJoinProductWithPage(storeId, productId, batchId, skipDate, skip, pageSize + 1);
                    if (CollectionUtils.isEmpty(storeProductBatchJoinProductBeanList)) break;
                    if (storeProductBatchJoinProductBeanList.size() <= pageSize) {
                        productMonitorService.parseAndSaveStoreProductBatchJoinProduct2Temp(storeProductBatchJoinProductBeanList, storeProductBatchJoinProductBeanList.size() - 1, now, storeIdMap);
                        logger.info("分页查询store_product_batch_full_join_product记录，当页记录数没有达到pageSize+1，没有下一页了，终止! storeId: {}, productId: {}, batchId: {}, page: {}, record: {}: pageSize + 1: {}",
                                storeId, productId, batchId, page + 1, storeProductBatchJoinProductBeanList.size(), pageSize + 1);
                        break;
                    } else {
                        logger.info("分页查询store_product_batch_full_join_product记录，当页记录数达到pageSize+1，还有下一页，继续! storeId：{}, productId:{}, batchId:{}, page: {}, record: {}: pageSize + 1: {}",
                                storeId, productId, batchId, page + 1, storeProductBatchJoinProductBeanList.size(), pageSize + 1);
                        productMonitorService.parseAndSaveStoreProductBatchJoinProduct2Temp(storeProductBatchJoinProductBeanList, storeProductBatchJoinProductBeanList.size() - 2, now, storeIdMap);
                        StoreProductBatchJoinProductBean lastOne = storeProductBatchJoinProductBeanList.get(storeProductBatchJoinProductBeanList.size() - 1);
                        storeId = lastOne.getStoreId();
                        productId = lastOne.getProductId();
                        batchId = lastOne.getBatchId();
                    }
                }
            }
            logs.add(String.format("截止到Step1结束耗时: %s", (System.currentTimeMillis() - start) / 1000));
            Integer version = productMonitorService.syncStoreProductBatchTemp2StoreProductMonitor(now);
            logs.add(String.format("截止到Step2结束耗时: %s", (System.currentTimeMillis() - start) / 1000));
            productMonitorService.deleteStoreProductMonitorWithOtherVersion(version);
            logs.add(String.format("截止到Step3结束耗时: %s", (System.currentTimeMillis() - start) / 1000));
            logs.forEach(log -> logger.info("商品临期过期监控时间统计: {}", log));
        } catch (Exception e) {
            logger.error("商品监控统计临期过期商品Task出错了!", e);
            productMonitorService.clearProductMonitorLock();
            return ResponseBaseVo.fail(ErrorCodeConstants.ERROR_CODE_901.getErrorCode(), ErrorCodeConstants.ERROR_CODE_901.getErrorMsg());
        }
        return ResponseBaseVo.ok();
    }

}