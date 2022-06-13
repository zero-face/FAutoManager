package cn.arros.server.constant;

/**
 * @author Zero
 * @date 2022/6/3 17:44
 * @description
 * @since 1.8
 **/
public enum GithubApi {
    P_CREATE_ORG_REPO("创建组织仓库", "https://api.github.com/orgs/{org}/repos"),

    P_DELETE_ORG_REPO("删除组织仓库", "https://api.github.com/repos/{owner}/{repo}"),

    P_CREATE_REPO_HOOK("创建仓库webhook", "https://api.github.com/repos/{owner}/{repo}/hooks"),

    G_GET_CODE("获取状态码", "https://github.com/login/oauth/authorize"),

    P_GET_TOKEN("获取token", "https://github.com/login/oauth/access_token"),

    G_GET_USERINFO("获取用户信息", "https://api.github.com/users/{username}");



    private String name;
    private String url = "";

    GithubApi(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
