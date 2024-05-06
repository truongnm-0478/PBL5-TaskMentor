package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.ChangePasswordRequest;
import dto.request.ClassRequest;
import dto.response.StudentResponse;
import model.ClassRoom;
import model.User;
import service.ClassService;
import service.StudentService;
import service.UserService;
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

@WebServlet("/api/change-password")
public class ChangePasswordController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final UserService userService = new UserService();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try {
                int userId = AuthorizationUtil.getUserId(request);
                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // parse JSON to User object
                ObjectMapper mapper = new ObjectMapper();
                ChangePasswordRequest changePasswordRequest = mapper.readValue(jsonRequest.toString(), ChangePasswordRequest.class);

                // teacher account
                User user = userService.changePassword(changePasswordRequest, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Change password successfully.", user);
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
        });
    }
}
