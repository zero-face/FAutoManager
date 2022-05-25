package cn.arros.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Verge
 * @Date 2021/11/4 20:35
 * @Version 1.0
 */
@SpringBootTest
class BuildTriggerControllerTest {
    @Autowired
    private BuildTriggerController buildTriggerController;

    @Test
    void trigger() throws Exception {
        buildTriggerController.tokenTrigger("c87d3546f5f9422bb4b9c9381c92d696", "48j359zja1");
    }
}