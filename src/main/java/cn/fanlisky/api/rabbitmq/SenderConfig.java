package cn.fanlisky.api.rabbitmq;

import cn.fanlisky.api.enums.QueueName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbitmq发送端配置
 *
 * @author zJiaLi
 * @date 2019-05-26 20:08
 */
@Slf4j
@Configuration
public class SenderConfig {

    static {
        log.info("Rabbitmq发送端配置成功");
    }

    @Bean
    public Queue queue() {
        return new Queue(QueueName.MAIN_QUEUE.getName());
    }

}
