package cn.fanlisky.api.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;


/**
 * 定时器线程配置类
 *
 * @author Gary
 * @since 2019-04-12 17:00
 */
@EnableScheduling
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("Schedule-Thread-").setDaemon(true).build();
        final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10, threadFactory);
        scheduledTaskRegistrar.setScheduler(scheduledThreadPoolExecutor);
    }
}
