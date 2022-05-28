package cn.arros.server.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPool {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        return new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES, workQueue);
    }
}
