package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.utils.WebsocketUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author: 张鹏
 * @date: 2022/6/25 0:26
 **/
@Slf4j
@Component
@ServerEndpoint("/websocket")
public class WebsocketController {


    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        WebsocketUtil.put(session.getId(), session);
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("客户端发送的消息是 >>>>>> {}", message);
    }


    /**
     * 关闭连接
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        WebsocketUtil.remove(session.getId());
    }

    /**
     * 异常方法
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("错误原因 >>>>>> {}", throwable.getMessage());
    }

}
