package cn.fanlisky.api.controller;

import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.service.WeChatService;
import cn.fanlisky.api.utils.WeChatUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *  微信公众号相关接口
 * @author Gary
 * @date 2019-04-06 12:27
 * <p>Code is my soul.<p/>
 */
@RestController
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @WebLog(title = Title.CHECK_WX_TOKEN)
    @GetMapping("/weChat")
    public String checkToken(@RequestParam(value = "signature") String signature,
                             @RequestParam(value = "timestamp") String timestamp,
                             @RequestParam(value = "nonce") String nonce,
                             @RequestParam(value = "echostr") String echostr)  {
        return WeChatUtil.checkSignature(signature, timestamp, nonce ) ? echostr : null;
    }

    @WebLog(title = Title.SEND_WX_MSG)
    @PostMapping("/weChat")
    public String getWxMsg(HttpServletRequest httpServletRequest){

        return weChatService.sendWxMsg(httpServletRequest);
    }
}
