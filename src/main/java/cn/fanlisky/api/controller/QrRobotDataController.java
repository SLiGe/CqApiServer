package cn.fanlisky.api.controller;

import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.domain.QrRobot;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.QrRobotDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Gary
 */
@RestController
@RequestMapping(value = "qr_robot_data")
public class QrRobotDataController {

    @Resource
    private QrRobotDataService service;

    @WebLog(title = Title.GET_ROBOT_END_NUM)
    @ApiOperation(value = "QQ机器人授权检测接口", notes = "QQ机器人授权检测接口")
    @GetMapping(value = "/getRobotEndNum")
    public RestfulResponse<List<QrRobot>> getDate() {
        List<QrRobot> endDate = service.getEndDate();
        RestfulResponse<List<QrRobot>> response = new RestfulResponse<>();
        if (endDate.isEmpty()) {
            response.setStatus("201").setMsg("未有授权到期");
        } else {
            response.setStatus("200").setMsg("查询成功").setData(endDate);
        }
        return response;
    }

    @WebLog(title = Title.GET_ROBOT_NOT_END)
    @ApiOperation(value = "QQ机器人获取未到期授权接口", notes = "QQ机器人获取未到期授权接口")
    @GetMapping(value = "/getRobotNotEnd")
    public RestfulResponse<List<Map<String, String>>> getNotEnd() {
        List<Map<String, String>> noEnd = service.getNoEnd();
        RestfulResponse<List<Map<String, String>>> response = new RestfulResponse<>();
        response.setData(noEnd).setMsg("查询成功").setStatus("200");
        return response;
    }
}
