package cn.fanlisky.api.constant;

/**
 * 微信配置常量
 * @author Gary
 * @since 2019-04-06 12:47
 * <p>Code is my soul.<p/>
 */
public class WeChatConfig {
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
    /**
     * 订阅事件-关注*/
    public static final String EVENT_TYPE_SUBSCRIBE = "SUBSCRIBE";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final String EVENT_TYPE_SCAN = "SCAN";
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 发送方帐号（一个OpenID）*/
    public static final String FROM_USER_NAME = "FromUserName";
    /**
     * 开发者微信号*/
    public static final String TO_USER_NAME = "ToUserName";
    public static final String MSG_TYPE = "MsgType";
    public static final String CONTENT = "Content";
    public static final String EVENT = "Event";
}
