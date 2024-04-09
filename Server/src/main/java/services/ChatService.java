package services;

import models.Message;
import repositories.ChatRepository;

import java.util.Date;

public class ChatService {

    private ChatRepository chatRepository; // Đối tượng repository để tương tác với database

    // Inject dependency
    public ChatService() {
        chatRepository = new ChatRepository(); // Khởi tạo đối tượng repository
    }

    public void saveMessage(String message) {
        // Tạo một đối tượng Message từ tin nhắn
        Message newMessage = new Message();
        newMessage.setContent(message);
        newMessage.setInsertTime(new java.sql.Timestamp(new Date().getTime())); // Set thời gian gửi tin nhắn

        // Lưu tin nhắn vào database thông qua repository
        chatRepository.save(newMessage);
    }
}
