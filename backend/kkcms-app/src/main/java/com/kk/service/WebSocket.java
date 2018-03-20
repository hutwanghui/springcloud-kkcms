package com.kk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.dao.MessageRespository;
import com.kk.entity.Message;
import com.kk.handler.MessageDecoder;
import com.kk.handler.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by msi- on 2018/3/2.
 */

@ServerEndpoint(value = "/chat", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
@Component
public class WebSocket {

    private static Logger log = LoggerFactory.getLogger(WebSocket.class);
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private final MessageRespository messageService;

    @Autowired
    public WebSocket(MessageRespository messageRespository) {
        this.messageService = messageRespository;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        System.out.println("=============获取数据============");
        //List<Message> list = messageService.findAll();
        //System.out.println("=============数据成功============" + list.toString());
        ObjectMapper mapper = new ObjectMapper();
        String Json = "";
        try {
            //Json = mapper.writeValueAsString(list);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        this.sendMessage(Json);
        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
    }


    @OnClose
    public void onClose() {
        webSockets.remove(this);
        ObjectMapper mapper = new ObjectMapper();
        String Json = "";
        try {
            Json = mapper.writeValueAsString(".....");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        this.sendMessage(Json);


        log.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
    }

    @OnMessage
    public void onMessage(Message message) {
        ObjectMapper mapper = new ObjectMapper();
        String Json = "";
        try {
            //Json = mapper.writeValueAsString();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        this.sendMessage(Json);
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
