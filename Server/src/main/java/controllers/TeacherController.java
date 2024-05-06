package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.UserUpdateRequest;
import dto.response.UserResponse;
import model.Teacher;
import model.User;
import service.TeacherService;
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
import java.util.Arrays;
import java.util.List;

@WebServlet("/api/teacher")
public class TeacherController extends HttpServlet {

    private final TeacherService teacherService = new TeacherService();
    private final RequestProcessor requestProcessor = new RequestProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestProcessor.processRequest(() -> {
            try {
                if(!AuthorizationUtil.checkUserRole(req,resp, 3)) {
                    return;
                }

                String id = req.getParameter("id");

                if (id != null && !id.isEmpty()) {
                    getTeacherById(req, resp, id);
                } else {
                    getAllTeacher(req, resp);
                }
            } catch (NumberFormatException e) {
                try {
                    ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid page number or page size format.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private void getAllTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!AuthorizationUtil.checkUserRole(req, resp, 3)) {
            return;
        }

        List<Teacher> teachers = teacherService.getListTeacher();

        ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "Teacher list successfully.", teachers);
    }

    private void getTeacherById(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        try {
//            int userId = Integer.parseInt(id);
//
//            UserResponse user = teacherService.get(userId);
//
//            if (user != null) {
//                ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "User information retrieved successfully.", user);
//            } else {
//                throw new IllegalArgumentException("User with id " + userId + " not found.");
//            }
        } catch (NumberFormatException e) {
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid user id format.");
        } catch (IllegalArgumentException e) {
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            System.out.println("e = " + e);
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving user information.");
        }
    }

}
