package cn.arros.server.controller;

import cn.arros.common.dto.HeartBeatBody;
import cn.arros.server.bo.ServiceInfo;
import cn.arros.server.common.CommonResult;
import cn.arros.server.component.ServiceContainer;
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
    public void receive(HeartBeatBody heartBeatBody) {
        container.refresh(heartBeatBody.getId());
    }

    /**
     * 服务注册
     * @param heartBeatBody 心跳包
     */
    @PostMapping("/register")
    public CommonResult register(@RequestBody HeartBeatBody heartBeatBody) {
        ServiceInfo serviceInfo = new ServiceInfo();
        BeanUtils.copyProperties(heartBeatBody, serviceInfo);

        return CommonResult.success("注册成功", container.register(serviceInfo));
    }
}
