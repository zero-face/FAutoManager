package cn.arros.server.controller;

import cn.arros.server.common.CommonResult;
import cn.arros.server.component.annotation.TraceBuild;
import cn.arros.server.entity.BuildInfo;
import cn.arros.server.entity.Repository;
import cn.arros.server.properties.ArrosProperties;
import cn.arros.server.service.BuildInfoService;
import cn.arros.server.service.BuildService;
import cn.hutool.json.JSONObject;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Verge
 * @Date 2021/10/31 10:11
 * @Version 1.0
 * @refactor zeroface
 * 触发分为三种：1. 首次创建仓库，触发克隆操作
 *            2. push触发pull
 *            3. 手动部署触发手动触发build（不用pull，因为push的时候已经是最新代码）
 */
@RestController
@RequestMapping("/server/trigger")
public class BuildTriggerController {

    protected Logger logger = LoggerFactory.getLogger(BuildTriggerController.class);

    @Autowired
    private BuildInfoService buildInfoService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private ArrosProperties arrosProperties;

    @PostMapping("/clone")
    public CommonResult test(HttpServletRequest request, @RequestBody JSONObject requestBody) throws IOException, GitAPIException {
        final String userAgent = request.getHeader("User-Agent");
        if(!userAgent.startsWith("GitHub-Hookshot/")) {
            return CommonResult.error();
        }
        logger.info("请求域名为: " + request.getServerName());
        //开启了白名单
        if(arrosProperties.getConfig("whiteTable").equals("1")) {
            if(!request.getServerName().endsWith("github.com")) {
                return CommonResult.error("对不起，你不能随意触发该接口");
            }
        }

        //获取触发的仓库地址
        final Repository repository = requestBody.get("repository", Repository.class);
        buildInfoService.fetchAndBuild(repository);

        System.out.println("====end====");
        return CommonResult.success();
    }

    @PostMapping("/pull")
    public CommonResult pull(HttpServletRequest request, @RequestBody JSONObject requestBody) throws IOException, GitAPIException {
        final String userAgent = request.getHeader("User-Agent");
        if(!userAgent.startsWith("GitHub-Hookshot/")) {
            return CommonResult.error();
        }
        logger.info("请求域名为: " + request.getServerName());
        //开启了白名单
        if(arrosProperties.getConfig("whiteTable").equals("1")) {
            if(!request.getServerName().endsWith("github.com")) {
                return CommonResult.error("对不起，你不能随意触发该接口");
            }
        }

        //获取触发的仓库地址
        final Repository repository = requestBody.get("repository", Repository.class);
        buildInfoService.pullAndBuild(repository);

        System.out.println("====end====");
        return CommonResult.success();
    }


    /**
     * token触发构建(无需鉴权)
     * @param id 项目ID
     * @param token token
     * 带着项目id以及适配的token用于校验
     */
    @TraceBuild
    @RequestMapping("/{id}/{token}")
    public CommonResult tokenTrigger(@PathVariable String id, @PathVariable String token) throws Exception {
        // 1.根据仓库ID寻找仓库地址

        // 2.拉取有更改的目录或者文件代码，构建

        // 3.发布到测试环境
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
     * 和自动构建不同的是，手动构建指的是发布这个操作，那么只需要将最近一次构建jar包上传到正式环境
     */
    @TraceBuild
    @RequestMapping("/{id}")
    public CommonResult manualTrigger(@PathVariable String id) throws Exception {
        BuildInfo buildInfo = buildInfoService.getById(id);
        if (buildInfo == null) throw new Exception("构建信息未找到");
        //开始构建
        // TODO： 据Log4j2官方文档说 However, as discussed in the Executors class and in other cases where thread pooling is
        // utilized, the ThreadContext may not always be automatically passed to worker threads. In those cases the
        // pooling mechanism should provide a means for doing so. The getContext() and cloneStack() methods can be
        // used to obtain copies of the Map and Stack respectively.
        CompletableFuture<Void> future = CompletableFuture.runAsync(
                new BuildService(buildInfo),
                threadPoolExecutor
        );

        future.whenComplete((unused, throwable) -> MDC.clear());

        return CommonResult.success("开始构建");
    }
}
