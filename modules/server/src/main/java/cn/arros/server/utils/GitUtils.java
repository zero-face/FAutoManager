package cn.arros.server.utils;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.properties.ArrosProperties;
import cn.hutool.extra.spring.SpringUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @Author Verge
 * @Date 2021/11/1 14:00
 * @Version 1.0
 */
public class GitUtils {
    private static final ArrosProperties arrosProperties = SpringUtil.getBean(ArrosProperties.class);

    private static final Logger log = LoggerFactory.getLogger(GitUtils.class);

    /**
     * 克隆仓库
     * @param url 远程仓库地址
     * @param filename 文件夹名
     * @return Git
     * @throws GitAPIException 异常
     */
    public static Git clone(String url, String filename) throws GitAPIException {
        System.out.println(arrosProperties);
        File file = new File(arrosProperties.getConfig(ConfigType.GIT).getConfigValue() + "/" +filename);
        log.info("从{}克隆至{}", url, file.getAbsolutePath());
        return Git.cloneRepository()
                .setURI(url)
                .setDirectory(file)
                .call();
    }

    /**
     * 根据仓库ID加载本地仓库
     * @param repoId 仓库ID
     * @return 仓库
     * @throws IOException 异常
     */
    public static Repository openLocalRepo(String repoId) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File repoDir = new File(arrosProperties.getConfig(ConfigType.GIT).getConfigValue() + "/" + repoId + "/.git");
        boolean isRepo = RepositoryCache.FileKey.isGitRepository(repoDir, FS.DETECTED);
        if(!isRepo) {
            throw new RepositoryNotFoundException("该目录不是git仓库");
        }
        return builder
                .setGitDir(repoDir)
                .readEnvironment()
                .findGitDir()
                .build();
    }

    /**
     * 从远程仓库拉取代码
     * @param repo 仓库
     * @return 成功与否
     * @throws GitAPIException
     */
    public static PullResult pull(Repository repo) throws GitAPIException {
        return new Git(repo)
                .pull()
                .call();
    }

    public static PullResult pull(String repoId) throws GitAPIException, IOException {
        Repository repo = openLocalRepo(repoId);
        return pull(repo);
    }


}
