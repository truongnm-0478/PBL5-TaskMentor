package services;

import models.Message;
import models.User;
import repositories.MessageRepository;

import java.util.Date;

public class ChatService {

    private final MessageRepository chatRepository = new MessageRepository();

    public Message createMessage(String content, User sender, User receiver) {

        Message newMessage = Message.builder()
                .content(content)
                .image(null)
                .isSeen(false)
                .sender(sender)
                .receiver(receiver)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(sender.getId())
                .updateTime(null)
                .updateBy(null)
                .deleteBy(null)
                .deleteTime(null)
                .build();

        chatRepository.save(newMessage);

        return newMessage;
    }
}
