package cn.arros.server.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Verge
 * @Date 2021/11/21 18:46
 * @Version 1.0
 */
public class LogQueue {
    //队列大小
    public static final int QUEUE_MAX_SIZE = 10000;
    private static final LogQueue logQueue = new LogQueue();
    //阻塞队列
    private final BlockingQueue<LogDto> queue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    public static LogQueue getInstance() {
        return logQueue;
    }
    /**
     * 消息入队
     */
    public void push(LogDto logDto) {
        this.queue.add(logDto);
    }
    /**
     * 消息出队
     */
    public LogDto pop() {
        LogDto result = null;
        try {
            result = this.queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
