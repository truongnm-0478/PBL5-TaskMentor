package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.NotificationClassResponse;
import dto.response.NotificationTeacherSendToClassResponse;
import dto.response.NotificationUserResponse;
import model.Notification;
import service.NotificationService;
import util.AuthorizationUtil;
import util.RequestProcessor;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@WebServlet("/api/notification-for-user")
public class NotificationByUser extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final NotificationService notificationService = new NotificationService();
    private final SendNotificationController sendNotificationController = new SendNotificationController();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);

                List<NotificationUserResponse> notifications = notificationService.getNotification(userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List notification.", notifications);

            }  catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
    }
}
