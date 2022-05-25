package cn.arros.server.controller;


import cn.arros.server.common.CommonResult;
import cn.arros.server.entity.BuildInfo;
import cn.arros.server.service.BuildInfoService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
@RestController
@RequestMapping("/server/buildInfo")
public class BuildInfoController {
    @Autowired
    private BuildInfoService buildInfoService;

    @PostMapping
    public CommonResult addBuildInfo(@RequestBody BuildInfo buildInfo) {
        buildInfoService.addBuildInfo(buildInfo);
        return CommonResult.success();
    }
}

