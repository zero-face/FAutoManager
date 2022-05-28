package cn.arros.server.controller;

import cn.arros.server.entity.BuildInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Verge
 * @Date 2021/11/1 21:52
 * @Version 1.0
 */
@SpringBootTest
class BuildInfoControllerTest {
    @Autowired
    private BuildInfoController buildInfoController;

    @Test
    void addBuildInfo() {
        BuildInfo buildInfo = new BuildInfo();
        // TODO: 获取所有RepoId后供用户选择
        buildInfo.setRepoId("bdd0b150d06c4b148735caf431aad4f4");
        buildInfo.setBuildCommand("mvn package");
        // TODO: 应使用JGit获取仓库分支供用户选择
        buildInfo.setBranch("master");
        buildInfoController.addBuildInfo(buildInfo);
    }
}