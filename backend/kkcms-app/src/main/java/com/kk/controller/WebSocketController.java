package com.kk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.dao.MessageRespository;
import com.kk.entity.Message;
import com.kk.entity.User;
import com.kk.handler.MessageDecoder;
import com.kk.handler.MessageEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by msi- on 2018/3/2.
 */
@Controller
public class WebSocketController {
    //通过simpMessagingTemplate向浏览器发送消息

    private final SimpMessagingTemplate template;
    private final MessageRespository messageRespository;

    /**
     * 构造函数注入
     *
     * @param template
     */
    @Autowired
    WebSocketController(SimpMessagingTemplate template, MessageRespository messageRespository, ObjectMapper objectMapper) {
        this.template = template;
        this.messageRespository = messageRespository;
    }


    /**
     * once this URL will be triggered —
     * we will simply send message to all clients subscribed to /chat subscription.
     *
     * @param message
     */
    @MessageMapping(value = "/send/message")
    public void onReceivedMessage(@Payload() Message message) {
        System.out.println("================【前端发送消息来了】" + message + "=================");
        this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date()) + "-" + message);

    }


}
