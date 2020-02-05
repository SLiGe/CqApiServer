package cn.fanlisky.api.controller;


import cn.fanlisky.api.domain.QrPointsLuckDrawPrize;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.IQrPointsLuckDrawPrizeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 奖品表 前端控制器
 * </p>
 *
 * @author Gary
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/api/qr-points-luck-draw-prize")
public class QrPointsLuckDrawPrizeController {

    @Resource
    private IQrPointsLuckDrawPrizeService qrPointsLuckDrawPrizeService;

    @ApiOperation(value = "添加奖品接口", notes = "添加奖品接口")
    @PostMapping("/addDrawPrize")
    public RestfulResponse addDrawPrize(@RequestBody QrPointsLuckDrawPrize pointsLuckDrawPrize) {
        return qrPointsLuckDrawPrizeService.addDrawPrize(pointsLuckDrawPrize);
    }

}

