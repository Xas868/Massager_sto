package com.javamentor.qa.platform.groupchat.websockets;

import com.javamentor.qa.platform.security.JwtUserDetailsService;
import com.javamentor.qa.platform.security.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker//включает обработку сообщений по WebSocket, возвращаемый брокером сообщений
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {
    JwtUtil jwtUtil;
    JwtUserDetailsService jwtUserDetailsService;



    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");// включения простого брокера сообщений в памяти чтобы возвращать обратно сообщения клиенту по направлениям с префиксом /topic
        config.setApplicationDestinationPrefixes("/app");//Он также объявляет префикс /app для сообщений, привязанных к методам, аннотированными @MessageMapping.
//        config.setUserDestinationPrefix("topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/broadcast");// регистрирует /broadcast как альтернативный вариант обмена сообщениями, когда WebSocket не доступен.
    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptorAdapter() {
//        @Override
//        public Message<?> preSend(Message<?> message, MessageChannel channel) {
//            SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, SimpMessageHeaderAccessor.class);
////            StompHeaderAccessor accessor
//            System.out.println("in override " + ((StompHeaderAccessor) accessor).getCommand());
//            if (StompCommand.CONNECT.equals(((StompHeaderAccessor) accessor).getCommand())) {
//
//                String authToken = accessor.getFirstNativeHeader("Authorization");
//                System.out.println("Header auth token: " + authToken);
//                Principal principal = (Principal) jwtUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(authToken));
//                if (Objects.isNull(principal))
//                    return null;
//                accessor.setUser(principal);
//            } else if (StompCommand.DISCONNECT.equals(((StompHeaderAccessor) accessor).getCommand())) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (Objects.nonNull(authentication))
//                    System.out.println("Disconnected Auth : " + authentication.getName());
//                else
//                    System.out.println("Disconnected Sess : " + accessor.getSessionId());
//            }
//            return message;
//        }
//
//        @Override
//        public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//            StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
//
//            // ignore non-STOMP messages like heartbeat messages
//            if (sha.getCommand() == null) {
//                System.out.println("postSend null command");
//                return;
//            }
//
//            String sessionId = sha.getSessionId();
//
//            switch (sha.getCommand()) {
//                case CONNECT:
//                    System.out.println("STOMP Connect [sessionId: " + sessionId + "]");
//                    break;
//                case CONNECTED:
//                    System.out.println("STOMP Connected [sessionId: " + sessionId + "]");
//                    break;
//                case DISCONNECT:
//                    System.out.println("STOMP Disconnect [sessionId: " + sessionId + "]");
//                    break;
//                default:
//                    break;
//
//            }
//        }
//    });

//}
}
