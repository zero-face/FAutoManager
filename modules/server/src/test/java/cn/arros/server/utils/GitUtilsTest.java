package cn.arros.server.utils;

import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Author Verge
 * @Date 2021/11/1 14:13
 * @Version 1.0
 */
@SpringBootTest
class GitUtilsTest {
    @Test
    void testClone() throws GitAPIException {
        System.out.println(GitUtils.clone("https://gitee.com/vergeee/static-repo","test"));
    }

    @Test
    void openLocalRepo() throws IOException {
        System.out.println(GitUtils.openLocalRepo("a3e6eb2ccaf1495f829730575c89e1d2"));
    }

    @Test
    void pull() throws GitAPIException, IOException {
        Repository repository = GitUtils.openLocalRepo("a3e6eb2ccaf1495f829730575c89e1d2");
        PullResult pullResult = GitUtils.pull(repository);
        System.out.println(pullResult.isSuccessful());
    }
}