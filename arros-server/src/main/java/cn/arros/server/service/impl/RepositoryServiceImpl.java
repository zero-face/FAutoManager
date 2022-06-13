package cn.arros.server.service.impl;

import cn.arros.server.config.request.RestTemplateHelper;
import cn.arros.server.constant.GithubApi;
import cn.arros.server.entity.Repository;
import cn.arros.server.entity.WebHook;
import cn.arros.server.entity.dto.HookConfig;
import cn.arros.server.entity.dto.WebHookInfo;
import cn.arros.server.mapper.RepositoryMapper;
import cn.arros.server.mapper.WebHookMapper;
import cn.arros.server.properties.ArrosProperties;
import cn.arros.server.service.RepositoryService;
import cn.arros.server.utils.GitUtils;
import cn.arros.server.utils.WebHookUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
@Service
public class RepositoryServiceImpl extends ServiceImpl<RepositoryMapper, Repository> implements RepositoryService {

    protected Logger logger = LoggerFactory.getLogger(RepositoryServiceImpl.class);

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private WebHookMapper webHookMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ArrosProperties arrosProperties;

    @Override
    public int addRepo(Repository repository) throws GitAPIException {
        GitUtils.clone(repository.getUrl(), repository.getId());
        return repositoryMapper.insert(repository);
    }

    /**
     * 创建仓库
     * @param repository
     * @param token
     * @return
     */
    @Override
    public boolean createRepo(Repository repository, String token) throws RuntimeException{
        logger.info("开始创建仓库");
        final RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
        //构建请求头
        final HttpHeaders headers = restTemplateHelper.buildHeaders(new HashMap<String, String>(){{
            put("Authorization", "token " + token);
        }});
        //替换路径变量
        final HashMap<String, String> pathParam = new HashMap<>();
        pathParam.put("org", repository.getOrgName());
        final String url = restTemplateHelper.buildPathParams(GithubApi.P_CREATE_ORG_REPO.getUrl(), pathParam);
        //设置请求体
        final HashMap<String, Object> body = new HashMap<>();
        body.put("name", repository.getName());
        body.put("description", repository.getDescription());
        body.put("private", repository.getPrivateRepo());
        body.put("has_wiki", true);
        body.put("auto_init", repository.getAutoInit());
        body.put("gitignore_template", repository.getGitignoreTemplate());
        final HttpEntity entiy = restTemplateHelper.buildEntity(body, headers);
        ResponseEntity<String> response =null;
        try {
            response = restTemplateHelper.executePost(url, entiy, restTemplate);
            if(response.getStatusCodeValue() == 201) {
                logger.info("创建" + repository.getName() + "仓库成功");
                final JSONObject jsonObject = new JSONObject(response.getBody());
                final String nodeId = jsonObject.getStr("id");
                final String gitUrl = jsonObject.getStr("html_url");
                repository.setId(nodeId);
                repository.setUrl(gitUrl);
            } else if(response.getStatusCodeValue() == 401){
                logger.error("需要Authorization");
                throw new RuntimeException("未登录");
            } else if(response.getStatusCodeValue() == 403){
                logger.error("没有创建仓库权限");
                throw new RuntimeException("没有创建仓库权限");
            } else if(response.getStatusCodeValue() == 422){
                logger.error("仓库已经存在");
                throw new RuntimeException("仓库已经存在");
            } else {
                logger.error("通用错误");
                throw new RuntimeException("通用错误");
            }
            repositoryMapper.insert(repository);
            //创建一个创建仓库事件（触发clone）
            createEventHook(repository, token);
            //创建一个push触发事件（触发pull）
            pushEventHook(repository, token);
            return true;
        } catch (Exception e) {
            logger.error("创建" + repository.getName() + "失败, {}", e.getMessage());
            if(!e.getMessage().startsWith("422")) {
                final Integer b = deleteRepo(repository, token);
                if(b < 0) {
                    logger.error("仓库删除失败");
                }
                logger.info("仓库删除成功");
                return false;
            }
            throw new RuntimeException("仓库已存在");
        }
    }
    /**
     * 创建一个push触发hook
     * @param repository
     * @param token
     */
    private void pushEventHook(Repository repository, String token) {
        final WebHookInfo webHookInfo = WebHookUtil.buildCommonHookInfo(arrosProperties.getConfig("hookUrl"),
                "json", "push");
        createHook(repository,token, webHookInfo);
    }


    private void repositoryEventHook(Repository repository, String token) {
        final WebHookInfo webHookInfo = WebHookUtil.buildCommonHookInfo(arrosProperties.getConfig("hookUrl"),
                "json", "repository");
        createHook(repository,token, webHookInfo);
    }


    private void createEventHook(Repository repository, String token) {
        final WebHookInfo webHookInfo = WebHookUtil.buildCommonHookInfo(arrosProperties.getConfig("hookUrl"),
                "json", "create");
        createHook(repository,token, webHookInfo);
    }

    @Override
    public Integer deleteRepo(Repository repository, String token) throws RuntimeException {
        logger.info("正在删除" + repository.getName());
        final RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
        //构建请求头
        final HttpHeaders headers = restTemplateHelper.buildHeaders(new HashMap<String, String>(){{
            put("Authorization", "token " + token);
        }});
        //替换路径变量
        final HashMap<String, String> pathParam = new HashMap<>();
        pathParam.put("owner", repository.getOrgName());
        pathParam.put("repo", repository.getName());
        final String url = restTemplateHelper.buildPathParams(GithubApi.P_DELETE_ORG_REPO.getUrl(), pathParam);
        try {
            restTemplateHelper.executeDelete(url, restTemplate);
        } catch (Exception e) {
            if(e.getMessage().startsWith("403")) {
                logger.error("管理员设置成员不可以删库 {}", e.getMessage());
                return -2;
            } else if(e.getMessage().startsWith("404")) {
                logger.error("没有找到该仓库 {}", e.getMessage());
                return -3;
            }
            logger.error("删除" + repository.getName() + "失败 {}", e.getMessage());
            return -1;
        }
        return 0;
    }


    /**
     * 创建webhook的逻辑，后续只需要传入仓库信息，用户token，以及webhook信息即可创建
     *
     * @param repository
     * @param token
     * @param info
     * @return
     */
    private boolean createHook(Repository repository, String token, WebHookInfo info) {
        logger.info("开始创建" + repository.getName() + "的webhook");

        final RestTemplateHelper restTemplateHelper = new RestTemplateHelper();
        //构建请求头
        final HttpHeaders headers = restTemplateHelper.buildHeaders(new HashMap<String, String>(){{
            put("Authorization", "token " + token);
        }});
        //替换路径变量
        final HashMap<String, String> pathParam = new HashMap<>();
        String orgName = repository.getOrgName();
        if(orgName == null || orgName == "") {
            pathParam.put("owner", repository.getCreator());
        }else {
            pathParam.put("owner", repository.getOrgName());
        }
        pathParam.put("repo", repository.getName());
        final String url = restTemplateHelper.buildPathParams(GithubApi.P_CREATE_REPO_HOOK.getUrl(), pathParam);
        //设置请求体
        final HashMap<String, Object> body = new HashMap<>();
        body.put("name", info.getName());
        body.put("config", info.getHookConfig());
        body.put("events", info.getEvents());
        body.put("active", info.isActive());
        final HttpEntity entity = restTemplateHelper.buildEntity(body, headers);
        try {
            final ResponseEntity<String> responseEntity = restTemplateHelper.executePost(url, entity, restTemplate);
            if(responseEntity.getStatusCodeValue() == 201) {
                logger.info(repository.getName() + "创建了一个webhook,触发事件为" + info.getEvents().toString());
                final WebHook webHook = new WebHook();
                BeanUtils.copyProperties(info.getHookConfig(), webHook);
                webHook.setRepoId(repository.getId());
                webHook.setEvents(info.getEvents().toString());
                webHookMapper.insert(webHook);
                return true;
            }
        } catch (Exception e) {
            logger.error("webhook创建失败 {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return false;

    }




    public boolean createWebhook(Repository repository, String token) throws RuntimeException{
        return true;
    }
}
