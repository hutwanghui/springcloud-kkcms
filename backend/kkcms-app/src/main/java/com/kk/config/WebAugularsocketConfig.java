package com.kk.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by msi- on 2018/3/2.
 */
@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("com.kk")
public class WebAugularsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     * case the URL for connection will be http://localhost:8080/socket/
     * allow server to receive requests from any origin.
     * And we told the we will use not “clean” websockets, but with SockJS.
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * when our client will send message through socket,
     * the URL to send will look approximately like this: http://localhost:8080/app/…/…
     * will have just one subscription — /chat.
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/chat");
    }

}
