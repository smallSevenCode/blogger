package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Jwt参数
 *
 * @author: 张鹏
 * @date: 2022/1/4 21:52
 **/
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 密钥
     */
    private String key;

    /**
     * 过期时间(单位:秒)
     */
    private long expiration;

    /**
     * 主题
     */
    private String subject;
    /**
     * 白名单
     */
    private List<String> white;

}
