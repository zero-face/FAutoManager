package cn.arros.server.log;

import org.apache.maven.shared.invoker.InvokerLogger;
import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * 用于将MavenInvoker输出的日志交给SLF4J 的logger输出
 * @Author Verge
 * @Date 2021/11/22 20:30
 * @Version 1.0
 */
public class SLF4JInvokerLogger implements InvokerLogger {
    private final Logger logger;

    public SLF4JInvokerLogger(Logger logger) {
        if (logger == null) {
            throw new NullPointerException( "missing logger" );
        }
        this.logger = logger;
    }

    private void log(Level level, String message, Throwable error){
        if (message == null && error == null) {
            return;
        }

        if (level.compareTo(Level.DEBUG) == 0) {
            logger.debug(message);
        } else if (level.compareTo(Level.INFO) == 0) {
            logger.info(message);
        } else if (level.compareTo(Level.WARN) == 0) {
            logger.warn(message);
        } else if (level.compareTo(Level.ERROR) == 0) {
            logger.error(message);
        } else {
            logger.error(message);
        }
    }

    @Override
    public void debug(String message) {
        log(Level.DEBUG, message, null);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        log(Level.DEBUG, message, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void info(String message) {
        log(Level.INFO, message, null);
    }

    @Override
    public void info(String message, Throwable throwable) {
        log(Level.INFO, message, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void warn(String message) {
        log(Level.WARN, message, null);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        log(Level.WARN, message, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void error(String message) {
        log(Level.ERROR, message, null);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void fatalError(String message) {
        log(Level.ERROR, message, null);
    }

    @Override
    public void fatalError(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    @Override
    public boolean isFatalErrorEnabled() {
        return true;
    }

    @Override
    public void setThreshold(int threshold) {

    }

    @Override
    public int getThreshold() {
        return Integer.MAX_VALUE;
    }
}
