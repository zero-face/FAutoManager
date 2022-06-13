package cn.arros.server.service;

import cn.arros.server.entity.BuildInfo;
import cn.arros.server.entity.Repository;
import com.baomidou.mybatisplus.extension.service.IService;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

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

    void fetchAndBuild(Repository repository) throws GitAPIException;

    void pullAndBuild(Repository repository) throws GitAPIException, IOException;
}
