package live.jointheconversation.demo.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;

@Configuration
//@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new QuestionHandler(), "/questions").withSockJS();
        registry.addHandler(new QuestionHandler(), "/liveThread").withSockJS();
    }

    public class QuestionHandler extends TextWebSocketHandler{
        private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception{
            sessions.add(session);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message){

            for(WebSocketSession s: sessions){
                try {
                    s.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/gs-guide-websocket").withSockJS();
//
//    }
}
