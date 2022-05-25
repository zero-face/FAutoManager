package cn.arros.server.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Verge
 * @Date 2021/11/3 15:47
 * @Version 1.0
 */
class CommandUtilsTest {

    @Test
    void execute() throws IOException {
        File file = new File("D://");
        CommandUtils.execute(file, "mvn package");
    }
}