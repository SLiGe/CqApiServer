package cn.fanlisky.api.controller;

import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.request.ShortedUrlRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.ShortenUrlResponse;
import cn.fanlisky.api.utils.ShortenUrlUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 第三方服务接口
 * @author LiGe
 * @since 2019-03-22 22:56
 * <p>Code is my soul.<p/>
 */
@RestController
@RequestMapping("third_data")
public class ThirdDataController {

    @Resource
    private ShortenUrlUtil shortenUrlUtil;

    @WebLog(title = Title.SHORT_URLS)
    @ApiOperation(value = "缩短多个链接接口",notes = "缩短多个链接接口")
    @PostMapping(value = "/shortUrls")
    public RestfulResponse shortenUrls(@RequestBody ShortedUrlRequest request) throws UnsupportedEncodingException {
        List<ShortenUrlResponse> responses = shortenUrlUtil.formatUrls(request.getUrls(), request.getCno(), false, null);
        return RestfulResponse.getRestfulResponse(StatusCode.RESPONSE_SUCCESS,responses);
    }


}
