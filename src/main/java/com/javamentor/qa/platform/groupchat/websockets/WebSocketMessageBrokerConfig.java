package com.javamentor.qa.platform.groupchat.websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker//включает обработку сообщений по WebSocket, возвращаемый брокером сообщений
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");// включения простого брокера сообщений в памяти чтобы возвращать обратно сообщения клиенту по направлениям с префиксом /topic
        config.setApplicationDestinationPrefixes("/app");//Он также объявляет префикс /app для сообщений, привязанных к методам, аннотированными @MessageMapping.
        config.setUserDestinationPrefix("topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/broadcast");// регистрирует /broadcast как альтернативный вариант обмена сообщениями, когда WebSocket не доступен.
    }

}
