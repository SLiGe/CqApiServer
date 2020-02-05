package cn.fanlisky.api.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.domain.QrAppVersion;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IQrAppVersionService;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * app版本表 前端控制器
 * </p>
 *
 * @author Gary
 * @since 2020-01-05
 */
@RestController
@RequestMapping("/api/qr-app-version")
@Validated
public class QrAppVersionController {

    private IQrAppVersionService appVersionService;

    @Autowired
    public QrAppVersionController(IQrAppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @WebLog(title = Title.GET_APP_VERSION)
    @PostMapping(value = "/getAppVersionByName")
    @ApiOperation(value = "获取APP版本接口", notes = "获取APP版本接口")
    public RestfulResponse<QrAppVersion> getAppVersionByName(@NotNull @RequestBody QrAppVersion qrAppVersion) {
        QrAppVersion appVersion = appVersionService.getOne(Wrappers.<QrAppVersion>lambdaQuery().eq(QrAppVersion::getAppCode, qrAppVersion.getAppCode()));
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, appVersion);
    }

    @GetMapping(value = "/getApp")
    @ApiOperation(value = "下载APP接口", notes = "下载APP接口")
    public void downApp( QrAppVersion qrAppVersion, HttpServletResponse response) {
        appVersionService.getApp(qrAppVersion, response);
    }

}

