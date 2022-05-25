package cn.arros.server.service.impl;

import cn.arros.server.entity.Repository;
import cn.arros.server.mapper.RepositoryMapper;
import cn.arros.server.service.RepositoryService;
import cn.arros.server.utils.GitUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.eclipse.jgit.api.errors.GitAPIException;
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
public class RepositoryServiceImpl extends ServiceImpl<RepositoryMapper, Repository> implements RepositoryService {
    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public int addRepo(Repository repository) throws GitAPIException {
        GitUtils.clone(repository.getGitUrl(), repository.getId());
        return repositoryMapper.insert(repository);
    }
}
