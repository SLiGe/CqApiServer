package cn.fanlisky.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import cn.fanlisky.api.annotation.RequestLimit;
import cn.fanlisky.api.enums.RedisPrefixEnum;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.utils.RedisCacheUtil;
import cn.fanlisky.api.utils.IpUtil;
import cn.fanlisky.api.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * API请求拦截
 *
 * @author zJiaLi
 * @since 2019-07-16 10:18
 */

@Slf4j
@Component
public class RequestLimitIntercept extends HandlerInterceptorAdapter {


    private RedisCacheUtil redisCacheUtil;

    public RequestLimitIntercept(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * isAssignableFrom() 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口
         * isAssignableFrom()方法是判断是否为某个类的父类
         * instanceof关键字是判断是否某个类的子类
         */
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //HandlerMethod 封装方法定义相关的信息,如类,方法,参数等
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 获取方法中是否包含注解
            RequestLimit methodAnnotation = method.getAnnotation(RequestLimit.class);
            //获取 类中是否包含注解，也就是controller 是否有注解
            RequestLimit classAnnotation = method.getDeclaringClass().getAnnotation(RequestLimit.class);
            // 如果 方法上有注解就优先选择方法上的参数，否则类上的参数
            RequestLimit requestLimit = methodAnnotation != null ? methodAnnotation : classAnnotation;
            if (requestLimit != null) {
                if (isLimit(request, requestLimit)) {
                    responseOut(response, RestfulResponse.getRestfulResponse(StatusCode.REQUEST_ERROR));
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 判断请求是否受限*
     */
    private boolean isLimit(HttpServletRequest request, RequestLimit requestLimit) {
        // 受限的redis 缓存key ,因为这里用浏览器做测试，我就用sessionId 来做唯一key,如果是app ,可以使用 用户ID 之类的唯一标识。
        // 暂时使用IP作为判断
        String limitKey = request.getServletPath() + "|" + IpUtil.getIpAddress(request);
        // 从缓存中获取，当前这个请求访问了几次
        String redisCount = redisCacheUtil.getPre(RedisPrefixEnum.API_LIMIT.getPrefix(), limitKey);
        //如果不存在Key TODO 此处有BUG待修改:key消失时自增，时间则为-1
        if (ObjectUtil.isNullOrEmpty(redisCount)) {
            //初始 次数
            redisCacheUtil.setPreWithExpireTime(RedisPrefixEnum.API_LIMIT.getPrefix(), limitKey, "1", requestLimit.second());
        } else {
            if (Integer.parseInt(redisCount) >= requestLimit.maxCount()) {
                return true;
            }
            // 次数自增
            //判断是否还存在，incr命令：如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
            String secondValue = redisCacheUtil.getPre(RedisPrefixEnum.API_LIMIT.getPrefix(), limitKey);
            if (secondValue != null) {
                redisCacheUtil.incrPre(RedisPrefixEnum.API_LIMIT.getPrefix(), limitKey);
            }
        }
        return false;
    }


    /**
     * 回写给客户端
     *
     * @param response
     * @param result
     * @throws IOException
     */
    private void responseOut(HttpServletResponse response, RestfulResponse result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        String json = JSONObject.toJSON(result).toString();
        out = response.getWriter();
        out.append(json);
    }
}
