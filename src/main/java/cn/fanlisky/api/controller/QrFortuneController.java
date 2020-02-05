package cn.fanlisky.api.controller;


import com.github.pagehelper.PageHelper;
import cn.fanlisky.api.annotation.RequestLimit;
import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.domain.QrFortune;
import cn.fanlisky.api.domain.QrFortuneData;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.request.GetFortuneRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.TableResponse;
import cn.fanlisky.api.service.IQrFortuneService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 运势表 前端控制器
 * </p>
 *
 * @author Gary
 * @since 2019-07-09
 */
@RestController
@RequestMapping("/api/qr-fortune")
@Validated
public class QrFortuneController {

    @Resource
    private IQrFortuneService qrFortuneService;

    @WebLog(title = Title.GET_FORTUNE_OF_TODAY)
    @RequestLimit
    @ApiOperation(value = "获取今日运势接口", notes = "获取今日运势接口")
    @PostMapping("/getFortuneOfToday")
    public RestfulResponse<QrFortune> getFortuneOfToday(@NotNull @RequestBody GetFortuneRequest request) {
        return qrFortuneService.getFortuneOfToday(request.getQq(), request.getIsOne(), request.getIsGroup(), false, request.getGroupNum(),request.getIsIntegral());
    }

    @WebLog(title = Title.OLD_GET_FORTUNE_OF_TODAY)
    @RequestLimit
    @ApiOperation(value = "获取今日运势老接口", notes = "获取今日运势老接口")
    @GetMapping("/get/{qq}")
    public RestfulResponse<QrFortune> getFortuneOfTodayOld(@NotNull @PathVariable @ApiParam("QQ号") String qq) {
        return qrFortuneService.getFortuneOfToday(qq, 0, 1, true, null,0);
    }

    @WebLog(title = Title.GET_FORTUNE_LIST)
    @ApiOperation(value = "获取用户运势列表接口", notes = "获取用户运势列表接口")
    @GetMapping("/getFortune.aspx")
    public TableResponse getUserFortuneOfToday(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "limit", defaultValue = "10") int limit
            , QrFortuneData qrFortuneData) {
        PageHelper.startPage(page, limit);
        return qrFortuneService.getUserFortuneOfToday(qrFortuneData);
    }

}

