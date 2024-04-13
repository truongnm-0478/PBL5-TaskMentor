package controllers;

import model.User;
import service.AuthService;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User currentUser = (User) request.getSession().getAttribute("currentUser");

            String username = currentUser.getUsername();

            AuthService.deleteRefreshToken(username);

            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "User logged out successfully", null);
        } catch (Exception e) {
            ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
        }
    }
}
