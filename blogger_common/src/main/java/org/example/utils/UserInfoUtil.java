package org.example.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.example.common.UserInfo;

/**
 * 隔离线程
 * 获取当前用户信息工具类
 *
 * @author: 张鹏
 * @date: 2022/1/4 20:34
 */
public class UserInfoUtil {

    private static final ThreadLocal<UserInfo> local = new TransmittableThreadLocal<>();

    public static void set(UserInfo currentUserInfo) {
        local.set(currentUserInfo);
    }

    public static UserInfo get() {
        return local.get();
    }

    public static void remove() {
        local.remove();
    }


}
