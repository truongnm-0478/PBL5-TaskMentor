package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.ClassRequest;
import dto.response.StudentResponse;
import model.ClassRoom;
import service.ClassService;
import service.StudentService;
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

@WebServlet("/api/student")
public class StudentController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);

                String code = request.getParameter("code");

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                List<StudentResponse> studentResponses = studentService.getListStudent(code);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List student", studentResponses);

            }  catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    System.out.println("e = " + e);
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try {
                if (!AuthorizationUtil.checkUserRole(request, response, 2)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);

                int id = Integer.parseInt(request.getParameter("id"));

                Boolean isDelete = studentService.removeStudentToClass(id);

                // Trả về thông báo thành công
                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Class deleted successfully.", isDelete);
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
