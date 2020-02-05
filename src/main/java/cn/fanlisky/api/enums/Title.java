package cn.fanlisky.api.enums;

import lombok.Getter;

/**
 * @author Gary
 * @since 2019-11-13 21:16
 * <p>Code is my soul.<p/>
 */
@Getter
public enum Title {
    /**
     * 默认
     */
    API("接口"),

    LOGIN("用户登录接口"),

    GET_MOVIE("搜索电影接口"),

    GET_RECOMMEND_MOVIE("获取推荐电影接口"),

    CHECK_WX_TOKEN("微信获取签名接口"),

    SEND_WX_MSG("发送微信信息接口"),

    SEND_EMAIL("发送邮件接口"),

    GET_FORTUNE_OF_TODAY("获取今日运势接口"),

    GET_FORTUNE_LIST("获取用户运势列表接口"),

    OLD_GET_FORTUNE_OF_TODAY("获取今日运势老接口"),

    GET_SENTENCE("获取优美句子接口"),

    GET_ROBOT_END_NUM("QQ机器人授权检测接口"),

    GET_ROBOT_NOT_END("QQ机器人获取未到期授权接口"),

    QQ_SIGN_IN("机器人签到接口"),

    QUERY_SIGN_IN_DATA("机器人查询签到接口"),

    ADD_SIGN_INTEGRAL("签到增加积分接口"),

    GET_SIGN_IN_LIST("获取签到列表接口"),

    SHORT_URLS("缩短链接接口"),

    GET_APP_VERSION("获取APP版本接口"),


    ;

    private String name;

    Title(String name) {
        this.name = name;
    }
}
