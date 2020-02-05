package cn.fanlisky.api.controller;

import cn.fanlisky.api.constant.MovieConfig;
import cn.fanlisky.api.domain.QrBiaoBai;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.SayLoveService;
import cn.fanlisky.api.utils.TCPUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @description: 表白墙请求
 * @author Gary
 * @since 2018-12-27 11:31
 */
@RestController
@RequestMapping("love")
public class QrSayLoveController {

    @Resource
    private SayLoveService sayLoveService;
    @Resource
    private MovieConfig movieConfig;

    @ApiOperation(value = "获取表白列表接口", notes = "获取表白列表接口")
    @GetMapping("/getList")
    public RestfulResponse getList(HttpServletRequest request) {
        return sayLoveService.getLoveList();
    }

    @ApiOperation(value = "添加表白接口", notes = "添加表白接口")
    @GetMapping("/add")
    public RestfulResponse addLove(HttpServletRequest request) {
        //表白内容
        String content = request.getParameter("content");
        //表白者
        String me = request.getParameter("me");
        //被表白者
        String you = request.getParameter("you");
        String qq = request.getParameter("qq");
        String sendContent = "$me$" + me + "$me$" + "$you$" + you + "$you$" + "$qq$" + you + "$qq$" + "$content$" + content + "$content$";
        //发送给酷Q响应
        TCPUtil.sendTCPRequest(movieConfig.socketCqpIp, movieConfig.socketCqpPort, sendContent, "gb2312");
        Date currentTime = new Date();
        QrBiaoBai qrBiaoBai = new QrBiaoBai().setYou(you).setQq(qq).setContent(content)
                .setMe(me).setAddtime(currentTime).setUptime(currentTime);
        return sayLoveService.addLove(qrBiaoBai);
    }

    @ApiOperation(value = "根据id获取表白接口", notes = "根据id获取表白接口")
    @GetMapping("/search")
    public RestfulResponse searchLove(@RequestParam("id") String id) {
        return sayLoveService.searchLove(id);
    }
}
