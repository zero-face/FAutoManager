package cn.arros.server.controller;

import cn.arros.server.component.LogCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/log")
    public void test(@RequestParam String id, @RequestParam String log) throws IOException {
        logger.info(id + "发来一条日志: " + log);
        logCollector.save(id,log);
    }
}
