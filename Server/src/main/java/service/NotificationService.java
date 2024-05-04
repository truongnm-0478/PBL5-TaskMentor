package service;

import dto.response.NotificationClassResponse;
import dto.response.NotificationUserResponse;
import dto.response.StudentResponse;
import lombok.Builder;
import model.*;
import repository.*;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NotificationService {
    private final NotificationRepository notificationRepository = new NotificationRepository();
    private final ClassRepository classRepository = new ClassRepository();
    private final JoinClassRepository joinClassRepository = new JoinClassRepository();

    private final StudentService studentService = new StudentService();

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
                    .id(notification.getId())
                    .build();
            list.add(nc);
        }
        return list;
    }

    public Boolean deleteNotification(int id, int userId) {
        Notification notification = notificationRepository.getById(id);
        notification.setDeleteBy(userId);
        notification.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        notificationRepository.update(notification);
        return true;
    }

     public List<Integer> getMemberUserIdsOfClass(String classCode) {
        List<StudentResponse> studentResponseList = studentService.getListStudent(classCode);

        List<Integer> userIds = new ArrayList<>();

        studentResponseList.stream()
                .map(StudentResponse::getUserId)
                .forEach(userIds::add);

        return userIds;
    }

    public List<NotificationUserResponse> getNotification(int userId) {
        List<Notification> notificationList = notificationRepository.getNotificationsByUserId(userId);
        List<NotificationUserResponse> responseList = new ArrayList<>();
        for(Notification notification : notificationList) {
            NotificationUserResponse notificationUserResponse = NotificationUserResponse.builder()
                    .id(notification.getId())
                    .classCode(notification.getClassRoom().getCode())
                    .className(notification.getClassRoom().getClassName())
                    .teacherName(notification.getClassRoom().getTeacher().getUser().getName())
                    .content(notification.getContent())
                    .insertTime(notification.getInsertTime())
                    .insertBy(notification.getInsertBy())
                    .build();
            responseList.add(notificationUserResponse);
        }
        return responseList;
    }
}
