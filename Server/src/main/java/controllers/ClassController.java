package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.ClassRequest;
import dto.response.AppointmentResponse;
import model.ClassRoom;
import model.User;
import service.ClassService;
import service.UserService;
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

@WebServlet("/api/class")
public class ClassController extends HttpServlet {

    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final ClassService classService = new ClassService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                if(!AuthorizationUtil.checkUserRole(request, response, 2)) {
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
                ClassRequest classInfo = mapper.readValue(jsonRequest.toString(), ClassRequest.class);

                ClassRoom classRoom = classService.saveClass(classInfo, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Class created successfully.", classRoom);
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);


                if (!AuthorizationUtil.checkUserRole(request, response, 2)) {
                    return;
                }

                List<ClassRequest> classRequests = classService.listClassForTeacher(userId);


                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List class", classRequests);

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
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try {
                if (!AuthorizationUtil.checkUserRole(request, response, 2)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);

                // Đọc dữ liệu từ JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // Parse JSON thành đối tượng ClassRequest
                ObjectMapper mapper = new ObjectMapper();
                ClassRequest classInfo = mapper.readValue(jsonRequest.toString(), ClassRequest.class);

                ClassRoom updatedClassRoom = classService.updateClass(classInfo, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Class updated successfully.", updatedClassRoom);
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try {
                if (!AuthorizationUtil.checkUserRole(request, response, 2)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);
                System.out.println("userId DELETE = " + userId);

                String code = request.getParameter("code");

                classService.deleteClass(code, userId);

                // Trả về thông báo thành công
                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Class deleted successfully.", null);
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
