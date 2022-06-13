//package cn.arros.server.utils;
//
//import cn.arros.server.constant.GithubApi;
//import cn.arros.server.utils.github.GithubHelper;
//import cn.hutool.json.JSONObject;
//import io.swagger.annotations.Authorization;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author Zero
// * @date 2022/6/3 19:40
// * @description
// * @since 1.8
// **/
//@SpringBootTest
//public class GithubHelperTest {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Test
//    void test() {
//        String str = "{userna}test{userna}";
//        final String zero = str.replaceAll("\\{userna}", "zero");
//        System.out.println(zero);
//        System.out.println(str);
//    }
//
//    @Test
//    void testGetCode() {
//        final GithubHelper githubHelper = new GithubHelper();
//        final HashMap<String, String> stringStringHashMap = new HashMap<String, String>(){{
//            put("client_id", "xxx");
//            put("login","zero-face");
//            put("scope", "repo");
//            put("allow_signup", "false");
//            put("state", "fdj235rtre123");
//            put("redirect_uri", "https://zeroface.cloud");
//        }};
//        final String url = githubHelper.buildGetPath(GithubApi.GET_CODE.getUrl(), stringStringHashMap);
//        final ResponseEntity<String> responseEntity = githubHelper.executeGet(url, restTemplate);
//        System.out.println(responseEntity.getHeaders().toString());
//        System.out.println(responseEntity.getBody());
//    }
//    @Test
//    void testGetToken() {
//        final GithubHelper githubHelper = new GithubHelper();
//        final Map<String, Object> pathParam = new HashMap<String, Object>() {{
//            put("client_id", "xxx");
//            put("client_secret", "xxxx");
//            put("code", "xxx");
//            put("redirect_uri", "https://zeroface.cloud");
//        }};
//        final HashMap<String, String> headerParam = new HashMap<>();
//        headerParam.put("Content-Type", "application/x-www-form-urlencoded");
//        headerParam.put("Connection", "keep-alive");
//
//        final String url = githubHelper.buildPathParams(GithubApi.GET_TOKEN.getUrl(), null);
//        final HttpHeaders headers = githubHelper.buildHeaders(headerParam);
//        final HttpEntity httpEntity = githubHelper.buildEntity(pathParam, headers);
//        final String reponse = githubHelper.executePost(url,httpEntity,restTemplate);
//        System.out.println(reponse);
//    }
//
//    @Test
//    void tetGet() {
//        final GithubHelper githubHelper = new GithubHelper();
//        final HashMap<String, String> path = new HashMap<>();
//        path.put("username", "zero-face");
//        final String url = githubHelper.buildPathParams(GithubApi.GET_USERINFO.getUrl(), path);
//        final ResponseEntity<String> responseEntity = githubHelper.executeGet(url, restTemplate);
//        System.out.println(responseEntity.getBody());
//
//    }
//}
