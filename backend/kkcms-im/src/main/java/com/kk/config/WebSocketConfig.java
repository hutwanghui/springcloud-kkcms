package com.kk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created by msi- on 2018/1/30.
 * webSocket整合的配置
 *
 * @EnableWebSocketMessageBroker注解开启使用STOMP协议来传输基于代理（message broker）的信息
 * 控制器使用@MessageMapping同使用@RequestMapping一样接受前端传来的信息
 */
/*@Configuration
@EnableWebSocketMessageBroker*/
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {    //注册STOMP协议的节点（endpoint）、并映射指定的URL
        registry.addEndpoint("/endpointWisely").withSockJS();   //注册一个STOMP的endpoint，并指定使用SockJS协议
        registry.addEndpoint("/endpointChat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {    //匹配消息代理（Message Broker）
        registry.enableSimpleBroker("/topic","/queue");  //广播
    }
}
