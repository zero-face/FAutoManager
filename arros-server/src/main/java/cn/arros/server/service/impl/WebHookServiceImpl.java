package cn.arros.server.service.impl;

import cn.arros.server.entity.WebHook;
import cn.arros.server.mapper.WebHookMapper;
import cn.arros.server.service.WebHookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Zero
 * @date 2022/6/4 20:46
 * @description
 * @since 1.8
 **/
@Service
public class WebHookServiceImpl extends ServiceImpl<WebHookMapper, WebHook> implements  WebHookService{
}
