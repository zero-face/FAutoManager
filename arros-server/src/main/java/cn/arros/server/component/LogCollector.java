package cn.arros.server.component;


import cn.arros.server.constant.ConfigType;
import cn.arros.server.properties.ArrosProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author Verge
 * @Date 2021/12/23 17:19
 * @Version 1.0
 * @description 将节点上的日志存在了文件中
 */
@Component
public class LogCollector {
    @Autowired
    private ArrosProperties arrosProperties;

    public void save(String id, String log) throws IOException {
        String logPath = arrosProperties.getConfig(ConfigType.LOG_PATH);
        File logFile = new File(logPath + "/" + id + "/" + new Date().getDate() + ".log");
        if(!logFile.exists()) {
            if (!logFile.createNewFile()) throw new RuntimeException("无法创建文件" + logFile);
        }

        FileOutputStream outputStream = new FileOutputStream(logFile, true);
        OutputStreamWriter osw = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        osw.write(log);

        osw.close();
    }
}
