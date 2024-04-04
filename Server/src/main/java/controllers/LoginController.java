package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.LoginResponse;
import models.User;
import services.AuthService;
import services.UserService;
import utils.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);

            if (authenticate(user.getUsername(), user.getPassword())) {
                String accessToken = AuthService.generateAccessToken(user);
                String refreshToken = AuthService.generateRefreshToken(user);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Login successful", new LoginResponse(accessToken, refreshToken));

            } else {
                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials", null);
            }
        } catch (Exception e) {
            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request", null);
        }
    }

    private boolean authenticate(String username, String password) {
        return userService.authenticateUser(username, password) != null;
    }
}
