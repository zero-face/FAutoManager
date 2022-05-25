package cn.arros.server.controller;

import cn.arros.server.common.CommonResult;
import cn.arros.server.entity.BuildInfo;
import cn.arros.server.service.BuildService;
import cn.arros.server.service.BuildInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Verge
 * @Date 2021/10/31 10:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/server/trigger")
public class BuildTriggerController {
    @Autowired
    private BuildInfoService buildInfoService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * token触发构建(无需鉴权)
     * @param id 构建信息ID
     * @param token token
     */
    @RequestMapping("/{id}/{token}")
    public CommonResult tokenTrigger(@PathVariable String id, @PathVariable String token) throws Exception {
        BuildInfo buildInfo = buildInfoService.getById(id);
        if (buildInfo == null) throw new Exception("构建信息未找到");
        if (!token.equals(buildInfo.getTriggerToken())) throw new Exception("token不匹配");
        //开始构建
        threadPoolExecutor.execute(new BuildService(buildInfo));
        return CommonResult.success("开始构建");
    }

    /**
     * 手动触发
     * @param id 构建id
     */
    @RequestMapping("/{id}")
    public CommonResult manualTrigger(@PathVariable String id) throws Exception {
        BuildInfo buildInfo = buildInfoService.getById(id);
        if (buildInfo == null) throw new Exception("构建信息未找到");
        //开始构建
        threadPoolExecutor.execute(new BuildService(buildInfo));
        return CommonResult.success("开始构建");

    }
}
