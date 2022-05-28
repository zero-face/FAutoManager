package cn.arros.server.service;

import cn.arros.server.entity.Node;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Verge
 * @since 2021-11-06
 */
public interface NodeService extends IService<Node> {
    int addNode(Node node);
}
