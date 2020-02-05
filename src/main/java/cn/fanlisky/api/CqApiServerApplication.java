package cn.fanlisky.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@MapperScan("cn.fanlisky.api.mapper")
public class CqApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqApiServerApplication.class, args);
    }

}
