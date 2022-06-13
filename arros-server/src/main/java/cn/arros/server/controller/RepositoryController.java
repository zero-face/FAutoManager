package cn.arros.server.controller;


import cn.arros.server.common.CommonResult;
import cn.arros.server.entity.Repository;
import cn.arros.server.entity.dto.HookConfig;
import cn.arros.server.service.RepositoryService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
@RestController
@RequestMapping("/server/repository")
public class RepositoryController {
    @Autowired
    private RepositoryService repositoryService;

    // TODO: 克隆过程应为异步，减少用户等待时间
    @PostMapping
    public CommonResult addRepo(@RequestBody @Validated Repository repository, HttpServletRequest request) throws RuntimeException {
        final String token = request.getHeader("Authorization");
        if(token == null) {
            throw new RuntimeException("请重新登录");
        }
        //调用开放api创建仓库并且返回仓库地址（当前版本只能创建github仓库，后续可以支持gitee或者公司内网代码仓库）
        //异步创建仓库，最后比较地址矫正
        final boolean repo = repositoryService.createRepo(repository, token);
        if(!repo) {
            return CommonResult.error("创建失败");
        }
        return CommonResult.success("创建成功");
    }

    @DeleteMapping
    public CommonResult deleteRepo(@RequestBody Repository repository, HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if(token == null) {
            throw new RuntimeException("请重新登录");
        }
        final Integer b = repositoryService.deleteRepo(repository, token);
        if(b == 0) {
            return CommonResult.success("删除成功");
        } else if(b == -2) {
            return CommonResult.error("请联系管理员删库");
        } else if(b == -3) {
            return CommonResult.error("仓库不存在");
        } else {
            return CommonResult.error("删除仓库失败");
        }

    }

    @PostMapping("/hook")
    public CommonResult createWebHook(@RequestBody Repository repository, HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if(token == null) {
            throw new RuntimeException("请重新登录");
        }

//        final boolean webhook = repositoryService.createWebhook(repository, token);
//        if(webhook) {
//            return CommonResult.success();
//        }
//        return CommonResult.error();
        return CommonResult.success();
    }

    @PutMapping
    public CommonResult updateRepo(@RequestBody Repository repository) throws Exception {
        if (StrUtil.isEmpty(repository.getId())) {
            throw new Exception();
        }
        repositoryService.updateById(repository);
        return CommonResult.success();
    }
}

