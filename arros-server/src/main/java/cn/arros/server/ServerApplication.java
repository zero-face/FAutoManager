package cn.arros.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Verge
 * @Date 2021/10/30 23:47
 * @Version 1.0
 */
@SpringBootApplication
public class ServerApplication {
    static {
        // MDC向子线程传递上下文
        System.setProperty("log4j2.isThreadContextMapInheritable", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
