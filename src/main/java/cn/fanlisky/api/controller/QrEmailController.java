package cn.fanlisky.api.controller;

import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.domain.server.Server;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.enums.Title;
import cn.fanlisky.api.model.request.SendMailRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.service.QrMessageService;
import cn.fanlisky.api.utils.SendMailUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 邮件相关
 * @author Gary
 * @since 2018/12/4 11:07
 */
@RestController
@RequestMapping("/api/qr_email")
public class QrEmailController {

    @Autowired
    private QrMessageService qrMessageService;
    @Autowired
    private SendMailUtil sendMailUtil;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @WebLog(title = Title.SEND_EMAIL)
    @ApiOperation(value = "发送邮件接口", notes = "发送邮件接口")
    @PostMapping(value = "/sendMail")
    public RestfulResponse sendMail(@RequestBody SendMailRequest sendMailRequest) {
        return qrMessageService.sendMail(sendMailRequest);
    }

    @ResponseBody
    @GetMapping("/sendServerMail")
    public RestfulResponse sendServer(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse) throws Exception {
        final WebContext webContext = new WebContext(servletRequest, servletResponse, servletRequest.getServletContext());
        Server server = new Server();
        try {
            server.copyTo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        webContext.setVariable("server", server);
        String process = thymeleafViewResolver.getTemplateEngine().process("email/server", webContext);
        SendMailRequest sendMailRequest = new SendMailRequest().setUserMail("357078415@qq.com")
                .setSendContent(process).setEmailTitle("系统状态");
        sendMailUtil.sendMailFun(sendMailRequest);
        return RestfulResponse.getRestfulResponse(StatusCode.SEND_SUCCESS);
    }
}
