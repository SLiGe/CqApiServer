package cn.fanlisky.api.controller;

import cn.fanlisky.api.annotation.RequestLimit;
import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.QrBeaSenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Gary
 * @since 2018/12/29 0:21
 */
@RestController
@RequestMapping("niuren")
public class QrGetBeaSenController {

    @Resource
    private QrBeaSenService service;

    @WebLog(title = Title.GET_SENTENCE)
    @RequestLimit(second = 1200, maxCount = 200)
    @ApiOperation(value = "获取优美句子接口", notes = "获取优美句子接口")
    @GetMapping("getSen")
    public RestfulResponse<String> getSentence() {

        return service.getSentence();
    }

}
