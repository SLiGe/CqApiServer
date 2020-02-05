package cn.fanlisky.api.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.fanlisky.api.domain.QrAppNotice;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IQrAppNoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 应用公告表 前端控制器
 * </p>
 *
 * @author Gary
 * @since 2020-02-02
 */
@RestController
@RequestMapping("/api/qr-app-notice")
public class QrAppNoticeController {

    private final IQrAppNoticeService qrAppNoticeService;

    public QrAppNoticeController(IQrAppNoticeService qrAppNoticeService) {
        this.qrAppNoticeService = qrAppNoticeService;
    }

    @ApiOperation(value = "查询应用公告", notes = "查询应用公告接口")
    @GetMapping(value = "getAppNoticeByAppCode")
    public RestfulResponse<QrAppNotice> getAppNoticeByAppCode(@RequestParam(value = "appCode") String appCode) {
        QrAppNotice appNotice = qrAppNoticeService.getOne(Wrappers.<QrAppNotice>lambdaQuery().eq(QrAppNotice::getAppCode, appCode));
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, appNotice);
    }
}

