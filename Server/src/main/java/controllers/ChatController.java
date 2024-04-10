package controllers;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
public class ChatController {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("WebSocket opened: " + session.getId());
        System.out.println("Connected clients: " + sessions.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        for (Session s : session.getOpenSessions()) {
            if (s.isOpen())
                s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("WebSocket closed: " + session.getId());
        System.out.println("Connected clients: " + sessions.size());
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }
}
