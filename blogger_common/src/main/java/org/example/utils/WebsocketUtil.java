package org.example.utils;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 储存websocket对象工具类
 *
 * @author: 张鹏
 * @date: 2022/6/26 1:31
 **/
@Slf4j
public class WebsocketUtil {

    // 储存Session实例对象
    private final static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    // 当前在线人数统计
    private final static AtomicInteger counter = new AtomicInteger();

    /**
     * 添加Session
     *
     * @param key
     * @param session
     */
    public synchronized static void put(String key, Session session) {
        sessionMap.put(key, session);
        log.info("ID {} 连接成功,当前在线人数 >>>>>> {}", key, counter.incrementAndGet());
    }

    /**
     * 移除并关闭Session
     *
     * @param key
     */
    public synchronized static void remove(String key) {
        try {
            Session session = sessionMap.remove(key);
            session.close();
            log.info("ID {} 关闭成功,当前在线人数 >>>>>> {}", key, counter.decrementAndGet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定key获取Session
     *
     * @param key
     * @return
     */
    public static Session get(String key) {
        return sessionMap.get(key);
    }

    /**
     * 获取所有Session
     *
     * @return
     */
    public static Map<String, Session> getAll() {
        return sessionMap;
    }

}
