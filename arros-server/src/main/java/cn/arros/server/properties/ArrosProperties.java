package cn.arros.server.properties;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.entity.SysConfig;
import cn.arros.server.service.ISysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Zero
 * @date 2021/11/12 20:25
 * @description 项目配置
 * @since 1.8
 **/
@Configuration
public class ArrosProperties {

    @Autowired
    private ISysConfigService sysConfigService;

    private final ConcurrentMap<String, String> configs = new ConcurrentHashMap<>();

    /**
     * 初始化加载所有配置到内存
     */
    @PostConstruct
    public void init() {
        sysConfigService.list()
                .forEach(config -> configs.put(config.getConfigKey(),config.getConfigValue()));
    }

    /**
     * 从数据库加载系统配置
     * @param configKeys
     */
    public void loadFromDB(String... configKeys) {
        for (String configKey : configKeys) {
            SysConfig sysConfig = sysConfigService.getOne(
                    new QueryWrapper<SysConfig>().eq("config_key", configKey)
            );
            configs.put(sysConfig.getConfigKey(), sysConfig.getConfigValue());
        }
    }

    /**
     * 根据配置名从获取配置
     * @param configKey
     * @return configVal
     */
    public String getConfig(String configKey) {
        if (configKey == null) {
            throw  new RuntimeException("configKey不能为空");
        }
        String configVal = configs.get(configKey);
        return Objects.requireNonNull(configVal);
    }

    /**
     * 根据配置类型拿取配置
     * @param configType
     * @return
     */
    public String getConfig(ConfigType configType) {
        return getConfig(configType.getConfigKey());
    }

    /**
     * 更新配置
     * @param config
     * @return
     */
    public boolean updateConfig(SysConfig config) {
       if (sysConfigService.updateById(config)) {
           loadFromDB(config.getConfigKey());
           return true;
       }
       return false;
    }
}
