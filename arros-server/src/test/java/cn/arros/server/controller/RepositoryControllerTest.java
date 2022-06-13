package cn.arros.server.controller;

import cn.arros.server.entity.Repository;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/**
 * @Author Verge
 * @Date 2021/11/1 20:06
 * @Version 1.0
 */
@SpringBootTest
class RepositoryControllerTest {
    @Autowired
    private RepositoryController repositoryController;

    @Test
    @Rollback
    void addRepo() throws GitAPIException {
        Repository repository = new Repository();
        repository.setUrl("https://gitee.com/vergeee/yiban-auto-checkin.git");
        repository.setName("易班自动打卡");
//        repositoryController.addRepo(repository);
    }

    @Test
    void updateRepo() throws Exception {
        Repository repository = new Repository();
        repository.setId("a3e6eb2ccaf1495f829730575c89e1d2");
        repository.setName("测试仓库1");
        repositoryController.updateRepo(repository);
    }
}