package cn.fanlisky.api.annotation;

import cn.fanlisky.api.enums.Title;

import java.lang.annotation.*;

/**
 * Web日志注解
 *
 * @author zJiaLi
 * @since 2019-08-01 14:30
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebLog {

    boolean flag() default true;


    Title title() default Title.API;
}
