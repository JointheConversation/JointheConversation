package live.jointheconversation.demo.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer{

    @Value("${app.origin}")
    private String origin;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/questions").setAllowedOrigins(origin).withSockJS();
        registry.addEndpoint("/liveThread").setAllowedOrigins(origin).withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic","/queue");
        registry.setApplicationDestinationPrefixes("/liveThread").enableSimpleBroker("/thread","queue");
    }

}
