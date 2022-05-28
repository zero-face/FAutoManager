package cn.arros.plugin.core.log.log4j2;

import cn.arros.plugin.core.component.HeartBeat;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

/**
 * @Author Verge
 * @Date 2021/12/6 19:17
 * @Version 1.0
 */
@Plugin(name = LogCollectorAppender.PLUGIN_NAME, category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class LogCollectorAppender extends AbstractAppender {
    public static final String PLUGIN_NAME = "ArrosLogCollector";

    protected LogCollectorAppender(String name,
                                Filter filter,
                                Layout<? extends Serializable> layout,
                                boolean ignoreExceptions,
                                Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    // TODO：可以用一个List暂存日志，达到一定阈值后发送
    @Override
    public void append(LogEvent event) {
        // 这个if的作用是判断当连接建立后再发送日志
        if (HeartBeat.getInstance() != null && HeartBeat.getInstance().getServiceId() != null) {
            String serviceId = HeartBeat.getInstance().getServiceId();
            String logStr = new String(getLayout().toByteArray(event));
            String serverHost = HeartBeat.getInstance().getServerHost();
            HttpRequest
                    .post(serverHost + "/log")
                    .form("id", serviceId)
                    .form("log", logStr)
                    .header(Header.CONNECTION,"Keep-Alive")
                    .execute();
        }


    }

    @PluginFactory
    public static LogCollectorAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                   @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                   @PluginElement("Filters") Filter filter) {
        if (name == null) {
            LOGGER.error("No name provided for WebSocketAppender");
            return null;
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new LogCollectorAppender(name, filter, layout, ignoreExceptions, Property.EMPTY_ARRAY);
    }
}
