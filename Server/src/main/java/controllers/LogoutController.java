package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.LogoutRequest;
import models.User;
import services.AuthService;
import services.UserService;
import utils.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/logout")
public class LogoutController extends HttpServlet {

    private final UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LogoutRequest logoutRequest = mapper.readValue(request.getInputStream(), LogoutRequest.class);

            String username = logoutRequest.getUsername();

            AuthService.deleteRefreshToken(username);

            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "User logged out successfully", null);
        } catch (Exception e) {
            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request", null);
        }
    }
}
