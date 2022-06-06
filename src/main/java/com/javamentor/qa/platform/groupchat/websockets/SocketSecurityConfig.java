//package com.javamentor.qa.platform.groupchat.websockets;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//@Configuration
//public class SocketSecurityConfig  extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//    @Override
//    protected boolean sameOriginDisabled() {
//        // We need to access this directly from apps, so can't do cross-site checks
//        return true;
//    }
//
//    @Override
//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//        messages.nullDestMatcher().authenticated()
//                .simpDestMatchers("/app/**").hasRole("USER")
//                .simpDestMatchers("/broadcast").authenticated();
//    }
//}
