package cn.arros.server.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Verge
 * @Date 2021/11/21 18:46
 * @Version 1.0
 */
public class LoggerQueue {
    //队列大小
    public static final int QUEUE_MAX_SIZE = Integer.MAX_VALUE;
    private static final LoggerQueue alarmMessageQueue = new LoggerQueue();
    //阻塞队列
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    public static LoggerQueue getInstance() {
        return alarmMessageQueue;
    }
    /**
     * 消息入队
     */
    public boolean push(String log) {
        return this.queue.add(log);
    }
    /**
     * 消息出队
     */
    public String pop() {
        String result = null;
        try {
            result = this.queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
