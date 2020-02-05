package cn.fanlisky.api.rabbitmq;

import com.alibaba.fastjson.JSON;
import cn.fanlisky.api.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : zJiaLi
 * @date : 2019-05-26 20:12
 */
@Slf4j
@Component
public class RabbitmqService {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public void setAmqpTemplate(AmqpTemplate amqpTemplate){
        this.amqpTemplate = amqpTemplate;
    }

    public <T>boolean sendToQueue(String queueName, T data){
        try{
            String msg = JSON.toJSONString(data);
            amqpTemplate.convertAndSend(queueName, msg);
            log.info("[RabbitMQ]----发送内容:{}",msg);
            return true;
        }catch (Exception e){
            log.warn("[RabbitMQ]----发送失败,e:{}", ExceptionUtil.getStackTrace(e));
            return false;
        }
    }
}
