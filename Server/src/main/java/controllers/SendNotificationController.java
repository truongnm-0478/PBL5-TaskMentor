package controllers;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.NotificationMessageResponse;

@ServerEndpoint(value="/notification")
public class SendNotificationController {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("New client connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Message from " + session.getId() + ": " + message);

        if (session.getUserProperties().get("userId") == null) {
            int userId = Integer.parseInt(message);
            session.getUserProperties().put("userId", userId);
            System.out.println("User ID " + userId + " has been saved for session " + session.getId());
        } else {
            // Handle other messages here
        }
    }

    public void sendNotificationToUser(String message, int userId) {
        for (Session client : clients) {
            if (userId == (int) client.getUserProperties().get("userId")) {
                try {
                    client.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendNotificationToListUsers(NotificationMessageResponse messageObject, List<Integer> userIds) {
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson;
        try {
            messageJson = objectMapper.writeValueAsString(messageObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        for (Session client : clients) {
            if (userIds.contains((int) client.getUserProperties().get("userId"))) {
                try {
                    client.getBasicRemote().sendText(messageJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    private boolean authenticate(String message) {
        return true;
    }
}
