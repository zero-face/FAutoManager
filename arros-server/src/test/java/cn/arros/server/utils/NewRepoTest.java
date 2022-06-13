package cn.arros.server.utils;

import cn.arros.server.common.CommonResult;
import cn.arros.server.config.request.RestTemplateHelper;
import cn.arros.server.constant.GithubApi;
import cn.arros.server.controller.RepositoryController;
import cn.arros.server.entity.Repository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Zero
 * @date 2022/6/3 23:07
 * @description
 * @since 1.8
 **/
@SpringBootTest
public class NewRepoTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RepositoryController repositoryController;

    @Test
    void testRep() {

        final RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
        final HttpHeaders headers = restTemplateHelper.buildHeaders(new HashMap<String, String>(){{
            put("Authorization", "token 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");
        }});
        final HashMap<String, String> pathParam = new HashMap<>();
        pathParam.put("org", "zeropoint-studio");
        final String url = restTemplateHelper.buildPathParams(GithubApi.P_CREATE_ORG_REPO.getUrl(), pathParam);
        final HashMap<String, Object> body = new HashMap<>();
        body.put("name", "test");
        body.put("description", "这是一个测试仓库，由github开放api创建");
        body.put("private", true);
        body.put("has_wiki", true);
        body.put("auto_init", true);
        body.put("gitignore_template", "Java");
        final HttpEntity entiy = restTemplateHelper.buildEntity(body, headers);
        final ResponseEntity response = restTemplateHelper.executePost(url, entiy, restTemplate);
        System.out.println(response);

    }

    @Test
    void addNewRepo() throws GitAPIException {
        final Repository repository = new Repository();
        repository.setName("SecondTestRepo");
        repository.setCreator("clzeroface");
        repository.setOrgName("zeropoint-studio");
        System.out.println(repositoryController.addRepo(repository, null));
    }
}
