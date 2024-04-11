package controllers;

import configurations.HttpSessionConfigurator;
import models.User;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket", configurator = HttpSessionConfigurator.class)
public class ChatController {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User) httpSession.getAttribute("currentUser");
        String userId = String.valueOf(user.getId());

        // Save session in Map
        sessions.put(userId, session);

        System.out.println("WebSocket opened for user: " + userId);
    }


    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        User user = (User) httpSession.getAttribute("currentUser");

        String senderId = String.valueOf(user.getId());
        String senderUsername = user.getUsername();

        // Gửi tin nhắn đến tất cả các người dùng khác trừ người gửi
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (!entry.getKey().equals(senderId)) {
                entry.getValue().getBasicRemote().sendText(senderUsername + ": " + message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (entry.getValue().equals(session)) {
                sessions.remove(entry.getKey());
                break;
            }
        }
        System.out.println("WebSocket closed");
    }


    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }
}

