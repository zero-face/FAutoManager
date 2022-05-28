package cn.arros.server.controller;

import cn.arros.server.component.LogCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author Verge
 * @Date 2021/12/6 16:52
 * @Version 1.0
 */
@RestController
public class LogCollectController {
    @Autowired
    private LogCollector logCollector;

    @PostMapping("/log")
    public void test(@RequestParam String id, @RequestParam String log) throws IOException {
        System.out.println(log);
        logCollector.save(id,log);
    }
}
