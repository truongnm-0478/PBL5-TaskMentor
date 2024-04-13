package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserAdminDTO;
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

@WebServlet("/api/user")
public class UserController extends HttpServlet {

    private final UserService userService = new UserService();
    private final RequestProcessor requestProcessor = new RequestProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if(!AuthorizationUtil.checkUserRole(req,resp, 3)) {
                return;
            }
            int pageNumber = Integer.parseInt(req.getParameter("page"));
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));

            if (pageNumber <= 0 || pageSize <= 0) {
                ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Page number and page size must be positive integers.");
                return;
            }

            List<UserAdminDTO> userList = userService.getAllUsers(pageNumber, pageSize);

            int totalUsers = userService.getTotalUsers();

            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            ResponseUtil.sendPagedJsonResponse(resp, HttpServletResponse.SC_OK, "User list retrieved successfully.", pageNumber, pageSize, totalUsers, totalPages, userList);
        } catch (NumberFormatException e) {
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid page number or page size format.");
        } catch (Exception e) {
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                if(!AuthorizationUtil.checkUserRole(request, response, 3)) {
                    return;
                }
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
                User user = mapper.readValue(jsonRequest.toString(), User.class);

                // teacher account
                User teacherAccount = userService.createTeacherAccount(user.getEmail(), user.getUsername(), user.getName(), user.getPhone(), userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Account created successfully.", teacherAccount);
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
