package cn.fanlisky.api.config;

import com.google.common.collect.Lists;
import cn.fanlisky.api.interceptor.GlobalHandlerInterceptor;
import cn.fanlisky.api.interceptor.RequestLimitIntercept;
import cn.fanlisky.api.interceptor.UserInterceptor;
import cn.fanlisky.api.mapper.QrRouteMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局拦截器配置类
 * @author  LiGe
 * @since  2019-03-23 00:20
 * <p>Code is my soul.<p/>
 */
@Configuration
public class GlobalInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private GlobalHandlerInterceptor globalHandlerInterceptor;
    @Resource
    private QrRouteMapper qrRouteMapper;
    @Resource
    private RequestLimitIntercept requestLimitIntercept;
    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final List<String> handlerUrls = qrRouteMapper.getHandledUrls();
        registry.addInterceptor(globalHandlerInterceptor).addPathPatterns(handlerUrls);
        registry.addInterceptor(requestLimitIntercept).addPathPatterns("/**");
        registry.addInterceptor(userInterceptor).addPathPatterns(getLoginPathPatterns());
    }


    private List<String> getLoginPathPatterns(){
        ArrayList<String> patterns = Lists.newArrayList();
        patterns.add("/user/**");
        patterns.add("/qr_admin_main/**");
        patterns.add("/qr_entertaining/**");
        patterns.add("/qr_index/**");
        patterns.add("/doc.html");
        return patterns;
    }
}
