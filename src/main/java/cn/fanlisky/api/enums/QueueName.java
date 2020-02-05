package cn.fanlisky.api.enums;

import lombok.Getter;

/**
 * 消息队列枚举
 *
 * @author zJiaLi
 * @date 2019-05-26 20:20
 */
@Getter
public enum QueueName {

    /**
     * 主要队列
     */
    MAIN_QUEUE("MAIN_QUEUE", "主要队列"),

    ;
    private String name;

    private String use;

    QueueName(String name, String use) {
        this.name = name;
        this.use = use;
    }
}
