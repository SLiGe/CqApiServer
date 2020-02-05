package cn.fanlisky.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import cn.fanlisky.api.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 全局请求拦截器
 *
 * @author Gary
 * @since 2019-03-23 00:17
 * <p>Code is my soul.<p/>
 */
@Slf4j
@Component
public class GlobalHandlerInterceptor implements HandlerInterceptor {

    private String signKey;

    @Value(value = "${api.signKey.md5}")
    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求中的token，使用md5加密
        String token = request.getHeader("token");
        if (signKey.equals(token)) {
            return true;
        }
        String localIp = IpUtil.getIpAddress(request);
        log.warn("[拦截器]----IP:{}非法调用接口", localIp);
        Map<String, String> errMsg = Maps.newHashMap();
        errMsg.put("status", "201");
        errMsg.put("msg", "非法调用");
        String errStr = JSON.toJSONString(errMsg);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(errStr);
        return false;
    }

}
