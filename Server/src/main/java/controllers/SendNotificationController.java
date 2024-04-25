package controllers;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

        // Kiểm tra và xác thực thông tin xác thực
        if (authenticate(message)) {
            // Xác thực thành công, thực hiện các hành động khác (nếu cần)
            System.out.println("Authentication successful");
        } else {
            // Xác thực thất bại, có thể đóng kết nối hoặc gửi thông báo lỗi
            System.out.println("Authentication failed");
        }
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    private boolean authenticate(String message) {
        // Phương thức này kiểm tra và xác thực thông tin xác thực từ message
        // Trả về true nếu thông tin xác thực hợp lệ, ngược lại trả về false
        // Đây là nơi bạn thực hiện xác thực thông tin nhận được từ client
        // Ví dụ: bạn có thể so sánh thông tin nhận được với cơ sở dữ liệu hoặc các cách thức xác thực khác
        // Trong trường hợp này, bạn có thể so sánh username và password với cơ sở dữ liệu hoặc thông tin được xác thực trước đó
        // Trong ví dụ này, chúng ta chỉ giả định xác thực luôn thành công
        return true;
    }
}
