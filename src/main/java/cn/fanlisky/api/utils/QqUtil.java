package cn.fanlisky.api.utils;

/**
 * QQ相关工具类
 *
 * @author Gary
 * @since 2019-05-11 22:17
 * <p>Code is my soul.<p/>
 */
public class QqUtil {

    private QqUtil() {
    }

    /**
     * 获取QQ头像地址
     */
    private static final String GET_QQ_HEAD = "http://q1.qlogo.cn/g?b=qq&nk=";

    /**
     * @param qq 使用QQ
     * @return 头像地址
     */
    public static String getGetQqHead(String qq) {
        return GET_QQ_HEAD + qq + "&s=640";
    }

    /**
     * 校验QQ号
     */
    public static boolean checkQqNum(String qq) {
        return qq.matches("[1-9]([0-9]{4,10})");
    }

}
