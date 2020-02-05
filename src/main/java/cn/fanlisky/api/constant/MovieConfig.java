package cn.fanlisky.api.constant;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 电影相关配置常量
 *
 * @author : zJiaLi
 * @since : 2019-07-11 15:48
 */
@Configuration
public class MovieConfig {


    @Value(value = "${socket.cqp.ip}")
    public String socketCqpIp;

    @Value(value = "${socket.cqp.port}")
    public String socketCqpPort;

    @Value(value = "${movie.pic.url:127.0.0.1}")
    public String moviePicUrl;

    @Value(value = "${movie.play.url:127.0.0.1}")
    public String moviePlayUrl;


}
