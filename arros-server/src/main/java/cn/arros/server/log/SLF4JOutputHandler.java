package cn.arros.server.log;

import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * 用于将Maven package时产生的日志交给SLF4J的logger输出
 * @Author Verge
 * @Date 2021/11/23 20:19
 * @Version 1.0
 */
public class SLF4JOutputHandler implements InvocationOutputHandler {
    private final Logger logger;

    public SLF4JOutputHandler(Logger logger) {
        if (logger == null) {
            throw new NullPointerException( "missing logger" );
        }
        this.logger = logger;
    }

    @Override
    public void consumeLine(String line) throws IOException {
        logger.info(line);
    }
}
