package com.ddmc.kanban.controller.gov.client;

import com.ddmc.core.view.ResponseBaseVo;
import com.ddmc.kanban.constant.Constants;
import com.ddmc.kanban.constant.ErrorCodeConstants;
import com.ddmc.kanban.request.product.monitor.ProductMonitorListRequestVo;
import com.ddmc.kanban.response.product.monitor.ProductMonitorSummaryResponseVo;
import com.ddmc.kanban.service.ProductMonitorService;
import com.ddmc.kanban.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品监控控制器
 *
 * @Author wude
 * @Create 2019-03-18 17:31
 */
@RestController
@RequestMapping(value = "api/gov/product/monitor")
public class ProductMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(ProductMonitorController.class);

    private final ProductMonitorService productMonitorService;
    private final VersionService versionService;

    public ProductMonitorController(ProductMonitorService productMonitorService, VersionService versionService) {
        this.productMonitorService = productMonitorService;
        this.versionService = versionService;
    }

    @PostMapping(value = "summary")
    public ResponseBaseVo<ProductMonitorSummaryResponseVo> summary() {
        try {
            return ResponseBaseVo.ok(productMonitorService.getProductMonitorySummary(versionService.getCurrVersion(Constants.PRODUCT_MONITOR_TASK_NAME_VERSION)));
        } catch (Exception e) {
            logger.error("获取临期过期商品监控情况出错了!", e);
            return ResponseBaseVo.fail(ErrorCodeConstants.ERROR_CODE_901.getErrorCode(), ErrorCodeConstants.ERROR_CODE_901.getErrorMsg(), null);
        }
    }

    @PostMapping("list")
    public ResponseBaseVo listByType(@RequestBody ProductMonitorListRequestVo request) {
        if (request.getType() == null) {
            return ResponseBaseVo.fail(ErrorCodeConstants.ERROR_CODE_000.getErrorCode(), ErrorCodeConstants.ERROR_CODE_000.getErrorMsg());
        }
        return ResponseBaseVo.ok(productMonitorService.pageQuery(
                request.getType(), request.getStationId(),
                versionService.getCurrVersion(Constants.PRODUCT_MONITOR_TASK_NAME_VERSION),
                request.getPage(), request.getPageSize()));
    }


}