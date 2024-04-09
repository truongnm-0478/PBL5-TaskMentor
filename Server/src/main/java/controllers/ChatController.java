package controllers;

import services.ChatService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatController extends HttpServlet {

    private final ChatService chatService = new ChatService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");

        // Lưu tin nhắn vào database thông qua service
        chatService.saveMessage(message);

        // Gửi tin nhắn tới WebSocket (nếu cần)
        // WebSocketService.send(message);

        // Phản hồi cho client
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Message received: " + message);

    }
}
