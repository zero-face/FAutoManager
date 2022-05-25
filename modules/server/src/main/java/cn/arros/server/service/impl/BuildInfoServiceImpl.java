package cn.arros.server.service.impl;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.entity.BuildInfo;
import cn.arros.server.mapper.BuildInfoMapper;
import cn.arros.server.properties.ArrosProperties;
import cn.arros.server.service.BuildInfoService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
@Service
public class BuildInfoServiceImpl extends ServiceImpl<BuildInfoMapper, BuildInfo> implements BuildInfoService {
    @Autowired
    private ArrosProperties arrosProperties;
    @Autowired
    private BuildInfoMapper buildInfoMapper;

    @Override
    public int addBuildInfo(BuildInfo buildInfo) {
        buildInfo.setId(IdUtil.fastSimpleUUID());
        buildInfo.setTriggerToken(RandomUtil.randomString(10));

        Path resultPath = Paths.get(arrosProperties.getConfig(ConfigType.GIT).getConfigValue(),
                buildInfo.getRepoId(),
                buildInfo.getResultPath());

        buildInfo.setResultPath(resultPath.toString());
        return buildInfoMapper.insert(buildInfo);
    }
}
