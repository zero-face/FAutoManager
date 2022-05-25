package cn.arros.server.component;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.properties.ArrosProperties;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Verge
 * @Date 2021/11/6 20:00
 * @Version 1.0
 */
@Configuration
public class AesEncryption {
    @Autowired
    private ArrosProperties arrosProperties;

    @Bean
    public SymmetricCrypto getSymmetricCrypto(){
        String key = arrosProperties.getConfig(ConfigType.AES).getConfigValue();
        byte[] byteKey = key.getBytes();
        return new SymmetricCrypto(SymmetricAlgorithm.AES,byteKey);
    }
}
