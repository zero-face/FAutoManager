package cn.arros.server.controller;

import cn.arros.common.dto.HeartBeatBody;
import cn.arros.server.bo.ServiceInfo;
import cn.arros.server.common.CommonResult;
import cn.arros.server.component.ServiceContainer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Verge
 * @Date 2021/12/4 23:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/beat")
public class BeatController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ServiceContainer container;

    /**
     * 获取所有注册进来的服务
     */
    @GetMapping
    private CommonResult getServices(){
        return CommonResult.success(container.listServices());
    }

    /**
     * 接受心跳
     * @param heartBeatBody 心跳包
     */
    @PostMapping
    public CommonResult receive(HeartBeatBody heartBeatBody) {
        logger.info("收到" + heartBeatBody.getId() +"的心跳");
        container.refresh(heartBeatBody.getId());
        return CommonResult.success("心跳检测成功", "pong");
    }

    /**
     * 服务注册
     * @param heartBeatBody 心跳包
     */
    @PostMapping("/register")
    public CommonResult register(@RequestBody HeartBeatBody heartBeatBody) {
        ServiceInfo serviceInfo = new ServiceInfo();
        BeanUtils.copyProperties(heartBeatBody, serviceInfo);
        logger.info(serviceInfo.getHost() + ":" +
                serviceInfo.getPort() + "的应用：" +
                serviceInfo.getName() +"发起注册");
        return CommonResult.success("注册成功", container.register(serviceInfo));
    }
}
