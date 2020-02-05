package cn.fanlisky.api.enums;

import lombok.Getter;

/**
 * Redis前缀枚举
 *
 * @author LiGe
 * @since 2019-03-23 01:15
 * <p>Code is my soul.<p/>
 */
@Getter
public enum RedisPrefixEnum {
    /**
     * 签到前缀
     */
    SIGN_IN("SIGN-IN-QQ"),

    /**
     * 电影名称
     */
    MOVIE("MOVIE-NAME"),

    /**
     * 登陆用户名
     */
    LOGIN_USER("LOGIN-USER"),

    /**
     * 微信存入电影名称
     */
    WX_MOVIE("WX-MOVIE-NAME"),

    /**
     * 用户运势
     */
    USER_FORTUNE("USER-FORTUNE"),

    /**
     * API接口限制
     */
    API_LIMIT("API-LIMIT"),

    /**
     * 数据总数
     */
    DATA_COUNT("DATA-COUNT"),

    /**
     * 登陆TOKEN
     */
    LOGIN_TOKEN("LOGIN-TOKEN"),


    /**
     * 开奖最新期数
     */
    LOTTERY_DRAW("LOTTERY-DRAW"),

    ;

    private String prefix;

    RedisPrefixEnum(String prefix) {
        this.prefix = prefix;
    }
}
