package cn.fanlisky.api.controller;


import cn.fanlisky.api.domain.QrLotteryUser;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IQrPointsLuckDrawRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 抽奖记录表 前端控制器
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/api/qr-points-luck-draw-record")
public class QrPointsLuckDrawRecordController {

    @Resource
    private IQrPointsLuckDrawRecordService qrPointsLuckDrawRecordService;

    @ApiOperation(value = "抽奖报名接口:QQ", notes = "抽奖报名接口:QQ")
    @PostMapping("/lotteryFromQq")
    public RestfulResponse lotteryFromQq(@RequestBody QrLotteryUser qrLotteryUser) {
        return qrPointsLuckDrawRecordService.lotteryFromQq(qrLotteryUser);
    }

    @ApiOperation(value = "执行抽奖", notes = "执行抽奖")
    @GetMapping("/executeDraw")
    public RestfulResponse executeDraw() {
        return qrPointsLuckDrawRecordService.executeDraw();
    }


}

