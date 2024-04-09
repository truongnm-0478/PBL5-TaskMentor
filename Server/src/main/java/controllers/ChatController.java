package controllers;

import models.Message;
import models.User;
import services.ChatService;
import repositories.UserRepository;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chat")
public class ChatController extends HttpServlet {

    private final ChatService chatService = new ChatService();
    private final UserRepository userRepository = new UserRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String content = request.getParameter("content");
            int senderId = Integer.parseInt(request.getParameter("senderId"));
            int receiverId = Integer.parseInt(request.getParameter("receiverId"));

            User sender = userRepository.getUserById(senderId);
            User receiver = userRepository.getUserById(receiverId);

            if (sender != null && receiver != null) {
                Message message = chatService.createMessage(content, sender, receiver);
                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Login successful", message);
            } else {
                ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid sender or receiver");
            }
        } catch (IllegalArgumentException e) {
            try {
                ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            try {
                ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
