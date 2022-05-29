package cn.arros.server.component;

import cn.arros.server.bo.ServiceInfo;
import cn.arros.server.constant.ServiceStatus;
import cn.arros.server.controller.vo.ServiceInfoVO;
import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务注册后存入容器
 * @Author Verge
 * @Date 2021/12/5 17:20
 * @Version 1.0
 */
@Component
@EnableScheduling
public class ServiceContainer {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceContainer.class);

    private final Map<String, ServiceInfo> container = new HashMap<>();

    /**
     * 查看容器中所有服务
     * @return 服务
     */
    public List<ServiceInfoVO> listServices() {
        return container.keySet().stream()
                .map(key -> {
                    ServiceInfoVO serviceInfoVO = new ServiceInfoVO();
                    BeanUtils.copyProperties(container.get(key), serviceInfoVO);
                    serviceInfoVO.setId(key);
                    return serviceInfoVO;
                }).collect(Collectors.toList());
    }

    /**
     * 服务首次需要注册
     * @param serviceInfo 服务信息
     * @return id
     */
    public String register(ServiceInfo serviceInfo) {
        serviceInfo.setTtl(System.currentTimeMillis() + 60000);
        serviceInfo.setStatus(ServiceStatus.ALIVE);
        String id = IdUtil.fastSimpleUUID();
        container.put(id, serviceInfo);
        return id;
    }

    /**
     * 刷新服务的状态
     * @param id
     */
    // TODO: 需要给用户一个友好的返回
    public void refresh(String id) {
        ServiceInfo serviceInfo = container.get(id);
        if (serviceInfo == null) {
            throw new RuntimeException("不存在该服务");
        }

        serviceInfo.setTtl(System.currentTimeMillis() + 60000);
    }

    /**
     * 每30s检测一次是否有死亡或复活服务
     * TODO 此处有bug，随着项目时间增长，几乎感知不到节点死亡
     */
    @Scheduled(fixedDelay = 30000)
    public void checkStatus() {
        LOGGER.debug("检测服务状态中...");
        container.keySet().stream()
                .map(container::get)
                .forEach(service -> {
                    Long ttl = service.getTtl();
                    ServiceStatus status = service.getStatus();
                    if (ttl > System.currentTimeMillis() && status == ServiceStatus.DEAD) {
                        service.setStatus(ServiceStatus.ALIVE);
                    }

                    if (ttl < System.currentTimeMillis() && status == ServiceStatus.ALIVE) {
                        service.setStatus(ServiceStatus.DEAD);
                    }
                });
    }
}
