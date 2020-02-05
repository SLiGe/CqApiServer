package cn.fanlisky.api.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import cn.fanlisky.api.domain.QrSignInData;
import cn.fanlisky.api.service.IMacUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zJiaLi
 * @date 2019-05-26 20:13
 */
@Slf4j
@Component
public class ReceiveConfig {

    @Resource
    private IMacUserService iMacUserService;

    @RabbitListener(queues = "MAIN_QUEUE")
    public void process(String data) {
        log.info("[RabbitMQ]----收到消息:{}", data);
        String checkJson = "{";
        if (!data.contains(checkJson)) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(data);
        String result = jsonObject.toJSONString();
        try {
            QrSignInData qrSignInData = JSONObject.parseObject(result, QrSignInData.class);
            iMacUserService.toUpdateUserPoint(qrSignInData);
        } catch (Exception e) {
            log.warn("[RabbitMQ]----异常:{}", ExceptionUtils.getMessage(e));
        }
    }
}
