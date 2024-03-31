package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;
import services.UserService;
import utils.PasswordHashingUtils;
import utils.ResponseUtil;

@WebServlet("/api/register")
public class RegisterController extends HttpServlet {
    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // read data from JSON
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonRequest.append(line);
        }

        // parser JSON to User Obj
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonRequest.toString(), User.class);

        try {
            userService.registerUser(user.getEmail(), user.getUsername(), user.getPassword(), user.getRole(), user.getName(), user.getPhone());
            // Trả về phản hồi thành công nếu đăng ký thành công
            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "User registered successfully.", null);
        } catch (IllegalArgumentException e) {
            // Trả về phản hồi lỗi nếu có lỗi trong quá trình đăng ký
            ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
