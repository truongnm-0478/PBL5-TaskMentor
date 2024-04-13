package service;

import model.Message;
import model.User;
import repository.MessageRepository;
import repository.UserRepository;

import java.util.Date;

public class MessageService {

    private final MessageRepository messageRepository = new MessageRepository();
    private static final UserRepository userRepository = new UserRepository();

    public void saveMessage(String senderId, String recipientId, String content) throws Exception {

        // Tìm người gửi và người nhận dựa trên id
        User sender = userRepository.getUserById(Integer.parseInt(senderId));
        User recipient = userRepository.getUserById(Integer.parseInt(recipientId));

        if (sender != null && recipient != null) {
            Message message = Message.builder()
                    .content(content)
                    .image(null)
                    .isSeen(false)
                    .sender(sender)
                    .receiver(recipient)
                    .insertTime(new java.sql.Timestamp(new Date().getTime()))
                    .insertBy(sender.getId())
                    .updateTime(null)
                    .updateBy(null)
                    .deleteBy(null)
                    .deleteTime(null)
                    .build();

            messageRepository.save(message);
        } else {
            throw new Exception("Sender or recipient not found");
        }
    }
}
