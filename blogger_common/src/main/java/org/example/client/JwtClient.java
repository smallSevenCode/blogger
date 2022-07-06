package org.example.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.common.Constant;
import org.example.common.UserInfo;
import org.example.properties.JwtProperties;
import org.example.redis.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Jwt令牌
 *
 * @author: 张鹏
 * @date: 2022/6/18 18:45
 **/
@Component
public class JwtClient {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisClient redisClient;

    /**
     * 生成token
     *
     * @param userInfo
     * @return
     */
    public String createToken(UserInfo userInfo) {
        String token = Jwts.builder()
                .setSubject(jwtProperties.getSubject())  // 主题
                .setIssuedAt(new Date())    // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * jwtProperties.getExpiration())) // 设置过期时间60s
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getKey())   // 密钥
                .claim(UserInfo.class.getTypeName(), JSONUtil.toJsonStr(userInfo))
                .compact();
        redisClient.setAndTime(token, jwtProperties.getExpiration(), TimeUnit.SECONDS, Constant.TOKEN, userInfo.getId());
        return token;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public UserInfo parseToken(String token) {
        UserInfo userInfo = null;
        try {
            // 解析token
            Claims claims = Jwts.parser().setSigningKey(jwtProperties.getKey()).parseClaimsJws(token).getBody();
            String userInfoJson = String.valueOf(claims.get(UserInfo.class.getTypeName()));
            userInfo = JSONUtil.toBean(userInfoJson, UserInfo.class);
            // 判断是否过期
            String authorize = redisClient.get(Constant.TOKEN, userInfo.getId());
            // 禁止重复登录
            Assert.isTrue(StrUtil.equals(authorize, token), "登录已过期");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        Assert.notNull(userInfo, "未登录");
        return userInfo;
    }


    /**
     * 白名单
     *
     * @return
     */
    public List<String> white() {
        return jwtProperties.getWhite();
    }


    /**
     * 鉴权
     *
     * @param userInfo
     */
    public void security(UserInfo userInfo) {
        if (CollUtil.isNotEmpty(userInfo.getRoleIdList())) {
            for (Long roleId : userInfo.getRoleIdList()) {
                if (redisClient.hasKey(userInfo.getPath(), "security", roleId)) {
                    return;
                }
            }
        }
        throw new RuntimeException("暂无权限");
    }
}
