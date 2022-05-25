package cn.arros.server.controller;


import cn.arros.server.common.CommonResult;
import cn.arros.server.entity.Repository;
import cn.arros.server.service.RepositoryService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.eclipse.jgit.api.errors.GitAPIException;
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
@RequestMapping("/server/repository")
public class RepositoryController {
    @Autowired
    private RepositoryService repositoryService;

    // TODO: 克隆过程应为异步，减少用户等待时间
    @PostMapping
    public CommonResult addRepo(@RequestBody Repository repository) throws GitAPIException {
        String uuid = IdUtil.fastSimpleUUID();
        repository.setId(uuid);
        repositoryService.addRepo(repository);
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

