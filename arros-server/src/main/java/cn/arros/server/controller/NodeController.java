package cn.arros.server.controller;


import cn.arros.server.common.CommonResult;
import cn.arros.server.entity.Node;
import cn.arros.server.service.NodeService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Verge
 * @since 2021-11-06
 */
@RestController
@RequestMapping("/server/node")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @PostMapping
    public CommonResult addNode(@RequestBody Node node) throws GitAPIException {
        nodeService.addNode(node);
        return CommonResult.success();
    }

}

