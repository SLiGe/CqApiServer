package cn.fanlisky.api.utils;

import java.util.UUID;

/**
 * @Description: UUID 工具类
 * @author  Gary
 * @date  2019-05-07 22:16
 * <p>Code is my soul.<p/>
 */
public class UUIDUtil {

    private UUIDUtil(){}

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String getUpUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
