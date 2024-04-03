package controllers;

import models.User;
import services.UserService;
import utils.JsonResponseUtil;
import utils.RequestProcessor;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/user")
public class UserController extends HttpServlet {

    private final UserService userService = new UserService();
    private final RequestProcessor requestProcessor = new RequestProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        requestProcessor.processRequest(() -> {
            try {
                List<User> userList = userService.getAllUsers();
                ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "User list retrieved successfully.", userList);
            } catch (IOException e) {
                try {
                    ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
