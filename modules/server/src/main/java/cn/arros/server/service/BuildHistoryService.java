package cn.arros.server.service;

import cn.arros.server.constant.BuildStatus;
import cn.arros.server.entity.BuildHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
public interface BuildHistoryService extends IService<BuildHistory> {
    boolean updateBuildStatus(String id, BuildStatus buildStatus);
}
