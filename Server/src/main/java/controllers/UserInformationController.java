package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.UserInfoResponse;
import dto.response.UserResponse;
import model.User;
import service.UserService;
import util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@WebServlet("/api/user-information/*")
public class UserInformationController extends HttpServlet {

    private final UserService userService = new UserService();
    private final RequestProcessor requestProcessor = new RequestProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestProcessor.processRequest(() -> {
            String pathInfo = req.getPathInfo();
            if (pathInfo != null && pathInfo.equals("/all")) {
                // Trường hợp gọi tất cả người dùng
                try {
                    List<UserInfoResponse> users = userService.getAllUserInfo();
                    if (!users.isEmpty()) {
                        ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "All users retrieved successfully.", users);
                    } else {
                        throw new IllegalArgumentException("No users found.");
                    }
                } catch (Exception e) {
                    try {
                        System.out.println("e = " + e);
                        ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving user information.");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                try {
                    int userId = AuthorizationUtil.getUserId(req);
                    UserResponse user = userService.getUserById(userId);
                    if (user != null) {
                        ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "User information retrieved successfully.", user);
                    } else {
                        throw new IllegalArgumentException("User with id " + userId + " not found.");
                    }
                } catch (NumberFormatException e) {
                    try {
                        ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid user id format.");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (IllegalArgumentException e) {
                    try {
                        ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (Exception e) {
                    try {
                        System.out.println("e = " + e);
                        ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving user information.");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}

