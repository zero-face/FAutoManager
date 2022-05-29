package cn.arros.plugin.core.component.annotation;

import java.lang.annotation.*;

/**
 * @author Zero
 * @date 2022/5/29 17:31
 * @description
 * @since 1.8
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryDot {
    /**
     * 重试次数
     * @return
     */
    int count() default 1;


    /**
     * 重试的间隔时间
     * @return
     */
    int sleep() default 0;


    /**
     * 是否支持异步重试方式
     * @return
     */
    boolean asyn() default false;
}