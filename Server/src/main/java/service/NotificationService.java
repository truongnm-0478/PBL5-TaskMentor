package service;

import dto.response.NotificationClassResponse;
import model.*;
import repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NotificationService {
    private final NotificationRepository notificationRepository = new NotificationRepository();
    private final ClassRepository classRepository = new ClassRepository();
    private final JoinClassRepository joinClassRepository = new JoinClassRepository();

    public boolean saveNotificationClass(String classCode, String content, int userId) throws Exception {
        try {
            ClassRoom classRoom = classRepository.findByCode(classCode);
            if (classRoom == null) {
                return false;
            }

            List<StudentClass> studentList = joinClassRepository.getClassesByClassCode(classCode);
            if (studentList.isEmpty()) {
                return false;
            }

            List<Integer> userIds = new ArrayList<>();
            for (StudentClass studentClass : studentList) {
                User user = studentClass.getStudent().getUser();
                if (user != null) {
                    userIds.add(user.getId());
                }
            }

            if (userIds.isEmpty()) {
                return false;
            }

            Notification notification = Notification.builder()
                    .classRoom(classRoom)
                    .userIds(userIds.toArray(new Integer[0]))
                    .isSeen(false)
                    .content(content)
                    .insertTime(new java.sql.Timestamp(new Date().getTime()))
                    .insertBy(userId)
                    .build();

            notificationRepository.saveNotification(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<NotificationClassResponse> getListNotificationByClass(String classCode) {
        List<Notification> notificationList = notificationRepository.getNotificationsByClassCode(classCode);
        List<NotificationClassResponse> list = new ArrayList<>();
        for(Notification notification : notificationList) {
            NotificationClassResponse nc = NotificationClassResponse.builder()
                    .name(notification.getClassRoom().getTeacher().getUser().getName())
                    .username(notification.getClassRoom().getTeacher().getUser().getUsername())
                    .content(notification.getContent())
                    .dateTime(notification.getInsertTime())
                    .build();
            list.add(nc);
        }
        return list;
    }
}
