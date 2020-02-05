package cn.fanlisky.api.enums;

import lombok.Getter;

/**
 * 状态码值
 *
 * @author Gary
 * <p>Code is my soul.</p>
 * @since 2019-04-01 15:46
 */
@Getter
public enum StatusCode {
    /**
     * 查询成功
     */
    QUERY_SUCCESS("20011", "查询成功"),

    /**
     * 查询失败
     */
    QUERY_FAILED("20010", "查询失败"),

    /**
     * 响应成功
     */
    RESPONSE_SUCCESS("200", "响应成功"),

    /**
     * 发送成功
     */
    SEND_SUCCESS("20021", "发送成功"),

    /**
     * 发送失败
     */
    SEND_FAILED("20020", "发送失败"),

    /**
     * 异常信息
     */
    GLOBAL_ERROR("500", "服务器升级维护中"),

    /**
     * 请求次数过多
     */
    REQUEST_ERROR("2500", "请求过于频繁，请稍后再试!"),

    /**
     * 添加成功
     */
    ADD_SUCCESS("20031", "添加成功"),

    /**
     * 添加失败
     */
    ADD_FAIL("20030", "添加失败"),


    /* -------用户--------*/
    /**
     * 登陆成功
     */
    LOGIN_SUCCESS("30011", "登陆成功"),

    /**
     * 登陆失败
     */
    LOGIN_FAILED("30010", "登陆失败"),

    /**
     * 用户已登录
     */
    LOGIN_ALREADY("30012", "用户已登录"),

    /**
     * 用户密码错误
     */
    LOGIN_PWD_FALSE("30014", "密码错误"),

    /**
     * 未注册
     */
    LOGIN_NO_USER("30015", "未查到此用户"),

    /**
     * 账号被锁定
     */
    LOGIN_USER_LOCKED("30016", "用户被锁定"),

    /**
     * 未知错误
     */
    LOGIN_UNKNOWN_ERROR("30017", "未知错误"),

    /**
     * 注册成功
     */
    REGISTER_SUCCESS("30021", "注册成功"),

    /**
     * 注册成功
     */
    REGISTER_FAILED("30020", "注册失败"),

    /**
     * 退出成功
     */
    LOGOUT_SUCCESS("30030", "退出成功"),

    /**
     * 未登陆
     */
    NOT_LOGIN("30040", "未登陆"),

    /*-----------------------------------------*/
    /**
     * 转让成功
     */
    TRANS_SUCCESS("30051", "转让成功"),

    /**
     * 转让失败
     */
    TRANS_FAIL("30050", "转让失败,请检查填写信息是否正确"),

    /**
     * 积分不足
     */
    TRANS_LESS("30052", "转让失败,所有积分不足！"),

    /*---------------抽奖------------------------*/
    LOTTERY_APPLY_FAIL("30060", "报名抽奖失败"),

    LOTTERY_APPLY_SUCCESS("30061", "报名抽奖成功"),

    LOTTERY_APPLY_REPEAT("30063", "请勿重复报名"),

    LOTTERY_NOT_PRiZE("30070", "未到抽奖时间,请留意机器人消息哦^-^ "),

    /*-----------------运势相关---------------*/
    FORTUNE_ALREADY("40010","运势操作已进行!"),

    /*-----------------文件相关---------------*/
    FILE_UPLOAD_SUCCESS("50010","文件上传成功"),
    FILE_UPLOAD_FAIL("50011","文件上传失败"),
    FILE_DOWNLOAD_SUCCESS("50020","文件下载成功"),
    FILE_DOWNLOAD_FAIL("20021","文件下载失败"),
    ;


    private String code;

    private String msg;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
