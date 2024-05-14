package controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.response.StudentResponse;
import service.StudentService;
import util.AuthorizationUtil;
import util.RequestProcessor;
import util.ResponseUtil;

@WebServlet("/api/student")
public class StudentController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {

            try {
                int userId = AuthorizationUtil.getUserId(request);

                String code = request.getParameter("code");

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                if (code == null) {
                    // Retrieve the student by ID
                    StudentResponse studentResponse = studentService.getStudentByUserId(userId);

                    // Check if the student exists
                    if (studentResponse == null) {
                        // If the student does not exist, return a not found response
                        ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Student not found.");
                        return;
                    }

                    // If the student exists, return the student response
                    ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Student retrieved successfully.", studentResponse);
                } else {
                    // If the ID parameter is not provided, return the list of students
                    List<StudentResponse> studentResponses = studentService.getListStudent(code);
                    ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List of students", studentResponses);
                }
            } catch (NumberFormatException e) {
                // If the student ID parameter cannot be parsed to an integer, return a bad request response
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid student ID format.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IllegalArgumentException e) {
                // Handle other exceptions
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                // Handle other exceptions
                try {
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
