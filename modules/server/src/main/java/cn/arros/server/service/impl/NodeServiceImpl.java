package cn.arros.server.service.impl;

import cn.arros.server.entity.Node;
import cn.arros.server.mapper.NodeMapper;
import cn.arros.server.service.NodeService;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Verge
 * @since 2021-11-06
 */
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {
    @Autowired
    private SymmetricCrypto crypto;
    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public int addNode(Node node) {
        String rawPwd = node.getPassword();
        String pwd = crypto.encryptBase64(rawPwd);
        node.setPassword(pwd);
        return nodeMapper.insert(node);
    }
}
