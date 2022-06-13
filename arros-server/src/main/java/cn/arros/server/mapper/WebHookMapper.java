package cn.arros.server.mapper;

import cn.arros.server.entity.WebHook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zero
 * @date 2022/6/4 20:44
 * @description
 * @since 1.8
 **/
@Mapper
public interface WebHookMapper extends BaseMapper<WebHook> {
}
