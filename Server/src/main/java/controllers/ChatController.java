package controller;

import configuration.HttpSessionConfigurator;
import model.User;
import service.MessageService;
import service.UserService;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket", configurator = HttpSessionConfigurator.class)
public class ChatController {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final MessageService messageService = new MessageService();
    private static final UserService userService = new UserService();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User) httpSession.getAttribute("currentUser");
        String userId = String.valueOf(user.getId());

        sessions.put(userId, session);

        System.out.println("WebSocket opened for user: " + userId);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        User sender = (User) httpSession.getAttribute("currentUser");
        String senderId = String.valueOf(sender.getId());

        String[] parts = message.split(":", 2);
        if (parts.length != 2) {
            return;
        }

        String recipientId = parts[0].trim();
        String content = parts[1].trim();

        User recipient = userService.getUserById(Integer.parseInt(recipientId));
        if (recipient == null) {
            session.getBasicRemote().sendText("Recipient not found");
            return;
        }

        try {
            messageService.saveMessage(senderId, recipientId, content);
        } catch (Exception e) {
            session.getBasicRemote().sendText("Error occurred while saving message: " + e.getMessage());
            return;
        }

        Session recipientSession = sessions.get(recipientId);
        if (recipientSession != null) {
            recipientSession.getBasicRemote().sendText(sender.getUsername() + ": " + content);
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
