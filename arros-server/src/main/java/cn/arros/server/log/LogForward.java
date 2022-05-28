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

    private final LogQueue logQueue = LogQueue.getInstance();

    @Bean
    public void pushLogs() {
        // TODO: 为了"/topic/buildLog"可以看到总日志，"/topic/buildLog/xxx"看到每次构建的日志，这里将一条消息发送了两次，有待改进
        threadPoolExecutor.execute(() -> {
            while (true) {
                LogDto logDto = logQueue.pop();
                String message = logDto.getLog();
                if (logDto.getBuildHistoryId() != null) {
                    String destination = "/topic/buildLog/" + logDto.getBuildHistoryId();
                    messagingTemplate.convertAndSend(destination, message);
                }
                messagingTemplate.convertAndSend("/topic/buildLog", message);
            }
        });
    }
}
