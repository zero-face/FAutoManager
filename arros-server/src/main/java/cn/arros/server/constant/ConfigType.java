package cn.arros.server.constant;

/**
 * @author Zero
 * @date 2021/11/12 21:16
 * @description
 * @since 1.8
 **/
public enum ConfigType {
    REPO_PATH("repo.path","Git仓库地址"),
    AES_KEY("aes.key","AES加密密钥"),
    BUILD_PATH("build.path","构建产物地址"),
    LOG_PATH("log.path","日志存放地址");

    private final String configKey;
    private final String des;

    ConfigType(String configKey, String des) {
        this.configKey = configKey;
        this.des = des;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getDes() {
        return des;
    }
}
