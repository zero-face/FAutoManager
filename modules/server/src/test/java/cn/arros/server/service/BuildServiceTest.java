package cn.arros.server.service;

import cn.hutool.core.util.RuntimeUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Verge
 * @Date 2021/11/2 23:55
 * @Version 1.0
 */
class BuildServiceTest {

    @Test
    void run() {
        System.out.println(RuntimeUtil.execForStr("powershell","mvn -v"));
    }
}