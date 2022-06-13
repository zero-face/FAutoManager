package cn.arros.server.service;

import cn.arros.server.entity.Repository;
import cn.arros.server.entity.dto.HookConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
public interface RepositoryService extends IService<Repository> {
    int addRepo(Repository repository) throws GitAPIException;

    boolean createRepo(Repository repository, String token) throws RuntimeException;

    Integer deleteRepo(Repository repository, String token) throws RuntimeException;

//    boolean createWebhook(Repository repository, String token) throws RuntimeException;
}
