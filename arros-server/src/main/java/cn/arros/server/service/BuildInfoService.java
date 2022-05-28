package cn.arros.server.service;

import cn.arros.server.entity.BuildInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
public interface BuildInfoService extends IService<BuildInfo> {
    /**
     * 增加新的构建信息
     * @param buildInfo 构建信息
     * @return 影响行数
     */
    int addBuildInfo(BuildInfo buildInfo);



}
