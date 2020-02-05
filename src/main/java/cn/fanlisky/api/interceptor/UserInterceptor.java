package cn.fanlisky.api.interceptor;


import com.alibaba.fastjson.JSONObject;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局请求拦截器
 *
 * @author Gary
 * @since 2019-03-23 00:17
 * <p>Code is my soul.<p/>
 */
@Component
public class UserInterceptor implements HandlerInterceptor {


    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Value(value = "${project.swagger.web.token}")
    private String swaggerToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String j_token = request.getHeader("j-token");
        if (!ObjectUtil.isNullOrEmpty(j_token)) {
            String userJson = redisCacheUtil.getPre(RedisPrefixEnum.LOGIN_TOKEN.getPrefix(), j_token);
            if (!ObjectUtil.isNullOrEmpty(userJson)) {
                return true;
            }
        }
        if (request.getRequestURI().contains("doc.html")) {
            String code = request.getParameter("code");
            if (swaggerToken.equals(code)) {
                return true;
            }
        }
        RestfulResponse restfulResponse = RestfulResponse.getRestfulResponse(StatusCode.NOT_LOGIN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JSONObject.toJSONString(restfulResponse));
        return false;
    }


}
