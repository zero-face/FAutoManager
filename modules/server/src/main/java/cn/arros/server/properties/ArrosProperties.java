package cn.arros.server.properties;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.entity.SysConfig;
import cn.arros.server.service.ISysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Zero
 * @date 2021/11/12 20:25
 * @description
 * @since 1.8
 **/
@Configuration
public class ArrosProperties {

    @Autowired
    private ISysConfigService sysConfigService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConcurrentMap<String,SysConfig> configs = new ConcurrentHashMap<>();

    /**
     * 初始化加载所有配置到内存
     */
    @PostConstruct
    public void init() {
        this.loadFromDB(ConfigType.values());
    }

    /**
     * 从数据库加载系统配置
     * @param types
     */
    public void loadFromDB(ConfigType... types) {
        if(null == types) {
            throw new RuntimeException("配置加载错误");
        }
        Arrays.stream(types).forEach(type -> {
            final SysConfig sysConfig = sysConfigService.getOne(new QueryWrapper<SysConfig>().eq(isContainType(type), "config_name", type.getName()));
            if(sysConfig != null) {
                configs.put(type.getName(), sysConfig);
            } else {
                logger.error(type.getName()+"配置文件加载错误");
            }
        });
    }

    /**
     * 根据配置类型数据库拿取配置
     * @param type
     * @return
     */
    public SysConfig getConfig(ConfigType type) {
        if(type.getName() == null) {
            throw new RuntimeException("请传入配置类型");
        }
        final SysConfig sysConfig = configs.get(type.getName());
        if(sysConfig == null) {
            throw new RuntimeException("系统暂不支持该类型配置");
        }
        return sysConfig;
    }

    /**
     * 根据配置名从数据库获取配置
     * @param type
     * @return
     */
    public SysConfig getConfig(String type) {
        if(type == null || type == "") {
            throw new RuntimeException("请传入配置类型");
        }
        final SysConfig sysConfig = configs.get(type.toUpperCase());
        if(sysConfig == null) {
            throw new RuntimeException("系统暂不支持该类型配置");
        }
        return sysConfig;
    }

    /**
     * 更新配置
     * @param type
     * @return
     */
    public boolean updateConfig(SysConfig type) {
        sysConfigService.update(new QueryWrapper<SysConfig>().eq(isContainType(type), "config_name", type.getConfigValue()));
        final ConfigType configType = ConfigType.valueOf(type.getConfigName().toUpperCase());
        this.loadFromDB(queryConfig(type.getConfigName()));
        return true;
    }

    /**
     * 判断是否包含支持配置类型
     * @param type
     * @return
     */
    private boolean isContainType(Object type) {
        for(ConfigType element :ConfigType.values()) {
            if(type instanceof String) {
                if(element.getName().equalsIgnoreCase((String) type)) {
                    return true;
                }
            } else if(type instanceof ConfigType) {
                if(element.equals(type)) {
                    return true;
                }
            } else {
                if(type.equals(element.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据名称获取内置配置类型
     * @param type
     * @return
     */
    private ConfigType queryConfig(String type) {
        for(ConfigType t : ConfigType.values()) {
            if(t.getName().equalsIgnoreCase(type)) {
                return t;
            }
        }
        return null;
    }
}
