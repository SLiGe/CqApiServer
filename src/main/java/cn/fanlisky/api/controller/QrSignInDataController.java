package cn.fanlisky.api.controller;


import com.github.pagehelper.PageHelper;
import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.domain.QrMsgOfDay;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.mapper.QrMsgOfDayMapper;
import cn.fanlisky.api.model.request.SignInForQqRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.TableResponse;
import cn.fanlisky.api.service.QrSignDataService;
import cn.fanlisky.api.utils.DateUtil;
import cn.fanlisky.api.utils.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * QQ签到表 前端控制器
 * </p>
 *
 * @author LiGe
 * @since 2019-03-21
 */
@RestController
@RequestMapping("niuren")
public class QrSignInDataController {

    @Resource
    private QrSignDataService qrSignDataService;

    @Resource
    private QrMsgOfDayMapper qrMsgOfDayMapper;

    @GetMapping("testA")
    public String testA() throws InterruptedException {
        for (int i = 0; i < 500; i++) {
            String content = HttpClientUtil.sendGet("https://chp.shadiao.app/api.php");
            QrMsgOfDay qrMsgOfDay = new QrMsgOfDay();
            qrMsgOfDay.setCreateDate(new Date());
            Date afterDate = DateUtil.getAfterDate(i, new Date());
            qrMsgOfDay.setSendDate(DateUtil.parseDateToStr(DateUtil.YYYY_MM_DD, afterDate));
            qrMsgOfDay.setSentence(content);
            qrMsgOfDayMapper.insert(qrMsgOfDay);
            Thread.sleep(500);
        }
        return "111";
    }


    @WebLog(title = Title.QQ_SIGN_IN)
    @ApiOperation(notes = "机器人签到接口", value = "机器人签到接口")
    @PostMapping(value = "/signIn")
    public RestfulResponse qqSignIn(@RequestBody SignInForQqRequest request) {
        return qrSignDataService.addSignIn(request);
    }

    @WebLog(title = Title.QUERY_SIGN_IN_DATA)
    @ApiOperation(notes = "机器人查询签到接口", value = "机器人查询签到接口")
    @PostMapping(value = "/querySignInData")
    public RestfulResponse getSignInData(@RequestBody SignInForQqRequest request) {
        return qrSignDataService.querySignInData(request);
    }

    @WebLog(title = Title.ADD_SIGN_INTEGRAL)
    @ApiOperation(notes = "签到增加积分接口", value = "签到增加积分接口")
    @PostMapping(value = "/addSignIntegral")
    public RestfulResponse addSignIntegral(@RequestBody SignInForQqRequest request) {
        return qrSignDataService.addSignIntegral(request);
    }

    @ApiOperation(value = "查询签到排行榜", notes = "查询签到排行榜")
    @PostMapping(value = "/getIntegralTop")
    public RestfulResponse getIntegralTop(@RequestBody Map<String, String> params) {
        return qrSignDataService.getIntegralTop(params);
    }

    /**
     * LayUi表格应为get请求,URL方式传参
     */
    @WebLog(title = Title.GET_SIGN_IN_LIST)
    @ApiOperation(value = "获取签到列表接口", notes = "获取签到列表接口")
    @GetMapping("/getSignInList")
    public TableResponse getSignInList(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit, QrSignInData qrSignInData) {
        PageHelper.startPage(page, limit);
        return qrSignDataService.getSignList(qrSignInData);
    }
}

