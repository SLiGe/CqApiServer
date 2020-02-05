package cn.fanlisky.api.handler;

import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.exception.DownFileException;
import cn.fanlisky.api.exception.UnknownAccountException;
import cn.fanlisky.api.model.response.RestfulResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * 公共异常处理类
 *
 * @author Gary
 * @since 2019-03-22 23:57
 * <p>Code is my soul.<p/>
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestfulResponse exceptionHandler(Exception e) {
        log.warn("接口请求错误, e-> {}", ExceptionUtils.getStackTrace(e));
        return RestfulResponse.getRestfulResponse(StatusCode.GLOBAL_ERROR);
    }

    /**
     * 拦截文件异常
     */
    @ExceptionHandler(DownFileException.class)
    @ResponseBody
    public RestfulResponse<Map> downFileExceptionHandler(DownFileException e) {
        log.warn("接口请求错误, e-> {}", ExceptionUtils.getStackTrace(e));
        Map<String, String> msg = new HashMap<String, String>(1) {{
            put("errMsg", e.getMessage());
        }};
        return RestfulResponse.getRestfulResponse(StatusCode.FILE_DOWNLOAD_FAIL, msg);
    }

    /**
     * 拦截登录异常
     */
    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public RestfulResponse loginUnknownException() {
        return RestfulResponse.getRestfulResponse(StatusCode.LOGIN_FAILED);
    }

    /**
     * 拦截HttpMessageNotReadableException类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public RestfulResponse messageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.warn("接口请求错误, e-> {}", ExceptionUtils.getStackTrace(e));
        return RestfulResponse.getRestfulResponse(StatusCode.GLOBAL_ERROR);
    }
}
