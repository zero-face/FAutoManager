package cn.arros.server.properties;

import cn.arros.server.constant.ConfigType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zero
 * @date 2021/11/15 12:16
 * @description
 * @since 1.8
 **/
@SpringBootTest
public class ArrosPropertiesTest {

    @Autowired
    private ArrosProperties arrosProperties;

    @Test
    void testValueOf() {
        System.out.println(arrosProperties.getConfig(ConfigType.GIT));
    }
}
