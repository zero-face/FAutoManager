package cn.arros.server.component.aop;

import cn.hutool.core.util.IdUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * 处理带有@TraceBuild注解的方法
 * @Author Verge
 * @Date 2021/11/27 19:03
 * @Version 1.0
 */
@Aspect
@Component
public class TraceBuild {

    @Pointcut("@annotation(cn.arros.server.component.annotation.TraceBuild)")
    private void withTraceThread() {};

    // 设置该次构建的ID
    @Before("withTraceThread()")
    public void beforeCall() {
        MDC.put("buildHistoryId", IdUtil.fastSimpleUUID());
    }
}
