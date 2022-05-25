package cn.arros.server.service.impl;

import cn.arros.server.constant.BuildStatus;
import cn.arros.server.entity.BuildHistory;
import cn.arros.server.mapper.BuildHistoryMapper;
import cn.arros.server.service.BuildHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
@Service
public class BuildHistoryServiceImpl extends ServiceImpl<BuildHistoryMapper, BuildHistory> implements BuildHistoryService {
    @Autowired
    private BuildHistoryMapper buildHistoryMapper;

    @Override
    public boolean updateBuildStatus(String id, BuildStatus buildStatus) {
        BuildHistory buildHistory = new BuildHistory();
        buildHistory.setId(id);
        buildHistory.setStatus(buildStatus.getCode());
        return buildHistoryMapper.updateById(buildHistory) == 1;
    }
}
