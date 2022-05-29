package cn.arros.plugin.core.component.aspect;

import cn.arros.plugin.core.component.HeartBeat;
import cn.arros.plugin.core.component.RetryTemplate;
import cn.arros.plugin.core.component.annotation.RetryDot;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Zero
 * @date 2022/5/29 17:32
 * @description
 * @since 1.8
 **/
@Aspect
@Component
public class RetryAspect {
    private final  Logger LOGGER = LoggerFactory.getLogger(HeartBeat.class);
    ExecutorService executorService = new ThreadPoolExecutor(3, 5,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>());


    @Around(value = "@annotation(retryDot)")
    public Object execute(ProceedingJoinPoint joinPoint, RetryDot retryDot) throws Exception {
        //注解反射后重新生成一个模板，然后将注册参数以及注解方法的逻辑放到其中，然后执行该模板
        RetryTemplate retryTemplate = new RetryTemplate() {
            @Override
            protected Object doBiz() throws Throwable {
                return joinPoint.proceed();
            }
        };
        retryTemplate.setRetryTime(retryDot.count())
                .setSleepTime(retryDot.sleep());

        if (retryDot.asyn()) {
            return retryTemplate.submit(executorService);
        } else {
            return retryTemplate.execute();
        }
    }
}