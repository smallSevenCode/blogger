package org.example.config;

import cn.hutool.core.thread.ThreadUtil;
import org.example.utils.WebsocketUtil;
import org.slf4j.utils.BlockingQueueUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * websocket配置类
 *
 * @author: 张鹏
 * @date: 2022/6/26 1:39
 **/
@Configuration
public class WebsocketConfig {

    /**
     * 开启websocket注解模式
     * 会扫描带有@ServerEndpoint注解的类
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    /**
     * 发送日志信息
     * 启动程序,该方法会自动执行
     */
    @PostConstruct
    public void sendLog() {
        // 开启多线程,避免阻塞程序启动
        ThreadUtil.execute(() -> {
            while (true) {
                // 获取阻塞队列中的消息,无消息时会进行阻塞
                String message = BlockingQueueUtil.poll();
                // 获取当前连接的websocket实例
                Map<String, Session> sessionMap = WebsocketUtil.getAll();
                sessionMap.forEach((key, session) -> {
                    // 发送消息
                    if (session.isOpen()) {
                        try {
                            session.getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
    }

}
