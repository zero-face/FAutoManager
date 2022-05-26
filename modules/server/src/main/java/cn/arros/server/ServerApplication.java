package cn.arros.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Verge
 * @Date 2021/10/30 23:47
 * @Version 1.0
 * TODO: 新增通过在平台上创建项目自动在github上创建仓库功能
 */
@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
