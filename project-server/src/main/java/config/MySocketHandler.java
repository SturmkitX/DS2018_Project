package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import utils.ChannelStatus;
import utils.ClientMessage;

import java.io.IOException;
import java.util.List;

public class MySocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("Closed connection on: " + session.getUri());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClientMessage decoded = mapper.readValue(message.getPayload(), ClientMessage.class);

        switch(decoded.getType()) {
            case "SUBSCRIBE": subscribeViewer(decoded.getChannel(), session); break;
            case "SEND": sendMessage(decoded.getChannel(), decoded.getContent()); break;
        }
    }

    private void subscribeViewer(String channel, WebSocketSession session) {
        ChannelStatus.insertSubscriber(channel, session);
    }

    private void sendMessage(String channel, String content) throws IOException {
        List<WebSocketSession> subs = ChannelStatus.getSubscribers(channel);

        ClientMessage message = new ClientMessage();
        message.setChannel(channel);
        message.setType("COMMENT");
        message.setContent(content);

        ObjectMapper mapper = new ObjectMapper();
        byte[] serialized = mapper.writeValueAsBytes(message);
        for (WebSocketSession session : subs) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(serialized));
            }
        }
    }
}
