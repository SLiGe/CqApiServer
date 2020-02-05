package cn.fanlisky.api.service;

import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.request.SendMailRequest;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Gary
 * @description: 发送邮件处理
 * @since 2018/12/4 10:44
 */
@Slf4j
@Service
public class QrMessageService {

    @Resource
    private SendMailUtil sendMailUtil;

    /**
     * @author: Gary
     * @description: 发送邮件
     * @params: [sendMailRequest]
     * @date: 10:57 2018/12/4
     **/
    public RestfulResponse sendMail(SendMailRequest sendMailRequest) {
        try {
            sendMailUtil.sendMailFun(sendMailRequest);
            return RestfulResponse.getRestfulResponse(StatusCode.SEND_SUCCESS);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            return RestfulResponse.getRestfulResponse(StatusCode.SEND_FAILED);
        }
    }


}
