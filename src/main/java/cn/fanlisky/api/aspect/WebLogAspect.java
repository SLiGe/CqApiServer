package cn.fanlisky.api.aspect;

import com.alibaba.fastjson.JSON;
import cn.fanlisky.api.annotation.WebLog;
import cn.fanlisky.api.domain.QrOperateLog;
import cn.fanlisky.api.mapper.QrOperateLogMapper;
import cn.fanlisky.api.model.response.RestfulResponse;
import cn.fanlisky.api.model.response.TableResponse;
import cn.fanlisky.api.utils.ExceptionUtil;
import cn.fanlisky.api.utils.IpUtil;
import cn.fanlisky.api.utils.ObjectUtil;
import cn.fanlisky.api.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 日志切面
 *
 * @author Gary
 * @date 2019-04-11 09:23
 */
@Aspect
@Slf4j
@Component
public class WebLogAspect {

    @Resource
    private QrOperateLogMapper operateLogMapper;

    @Pointcut(value = "@annotation(cn.fanlisky.api.annotation.WebLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "reValue")
    public void doAfterReturning(JoinPoint joinPoint, Object reValue) {
        handleLog(joinPoint, null, reValue);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    @Transactional(rollbackFor = Exception.class)
    protected void handleLog(JoinPoint joinPoint, Exception e, Object o) {
        WebLog webLog = getAnnotationLog(joinPoint);
        if (webLog == null) {
            return;
        }
        HttpServletRequest request = ServletUtils.getRequest();
        QrOperateLog operateLog = new QrOperateLog();
        operateLog.setTitle(webLog.title().getName());
        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operateLog.setMethod(className + "." + methodName + "()");
        operateLog.setOperateIp(IpUtil.getIpAddress(request));
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            operateLog.setOperateParam(JSON.toJSONString(joinPoint.getArgs()[0]));
        }
        operateLog.setOperateTime(new Date());
        operateLog.setOperateUrl(request.getRequestURI());
        operateLog.setRequestMethod(request.getMethod());
        if (!ObjectUtil.isNullOrEmpty(o)) {
            operateLog.setJsonResult(JSON.toJSONString(o));
            if (o instanceof RestfulResponse) {
                int code = ((RestfulResponse) o).getCode();
                if (code == 200) {
                    operateLog.setStatus(1);
                } else {
                    operateLog.setStatus(0);
                }
            } else if (o instanceof TableResponse) {
                int status = ((TableResponse) o).getStatus();
                if (status == 200) {
                    operateLog.setStatus(1);
                } else {
                    operateLog.setStatus(0);
                }
            }
        }
        if (e != null) {
            operateLog.setStatus(0);
            operateLog.setErrorMsg(ExceptionUtil.getStackTrace(e));
        }
        log.info("Description : {}", webLog.title().getName());
        log.info("URL : {}", request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}", request.getMethod());
        log.info("IP : {}", IpUtil.getIpAddress(request));
        log.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));
        log.info("RESPONSE : {}", o);
        try {
            operateLogMapper.insert(operateLog);
        } catch (Exception ex) {
            log.warn("[日志记录]----插入日志失败,e-> {}", ExceptionUtil.getStackTrace(ex));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private WebLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(WebLog.class);
        }
        return null;
    }
}
