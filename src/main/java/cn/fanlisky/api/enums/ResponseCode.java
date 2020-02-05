package cn.fanlisky.api.enums;

import lombok.Getter;

/**
 * @author zJiaLi
 * @since 2019-08-26 11:15
 */
@Getter
public enum ResponseCode {


    /**
     * 响应成功
     */
    RESPONSE_SUCCESS(200, "响应成功"),

    RESPONSE_FAIL(500, "获取失败");

    private int code;

    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
