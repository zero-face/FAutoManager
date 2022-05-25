package cn.arros.server.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Verge
 * @Date 2021/11/21 14:18
 * @Version 1.0
 */
@Component
public class LogForward {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private final LoggerQueue loggerQueue = LoggerQueue.getInstance();

    @Bean
    public void pushLogs() {
        threadPoolExecutor.execute(() -> {
            while (true) {
                String message = loggerQueue.pop();
                messagingTemplate.convertAndSend("/topic/log", message);
            }
        });
    }
}
