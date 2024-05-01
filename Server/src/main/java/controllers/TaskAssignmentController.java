package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.PlanRequest;
import dto.request.StatusRequest;
import dto.request.TaskAssignmentRequest;
import dto.request.TaskRequest;
import dto.response.TaskAssignmentResponse;
import dto.response.TaskResponse;
import model.Planning;
import model.Task;
import model.TaskAssignment;
import service.ProjectService;
import service.TaskService;
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

@WebServlet("/api/assignment")
public class TaskAssignmentController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final ProjectService projectService = new ProjectService();
    private static final TaskService taskService = new TaskService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(1, 0, 2);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                String taskId = request.getParameter("id");

                TaskAssignmentResponse taskAssignment = taskService.getTaskAssignment(Integer.parseInt(taskId));


                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Get task successfully.", taskAssignment);
            } catch (IllegalArgumentException e) {
                try {
                    e.printStackTrace();
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
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

                ObjectMapper mapper = new ObjectMapper();
                TaskAssignmentRequest taskAssignmentRequest = mapper.readValue(jsonRequest.toString(), TaskAssignmentRequest.class);

                Task task = taskService.updateTaskAssignment(taskAssignmentRequest, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Update successfully.", task);
            } catch (IllegalArgumentException e) {
                try {
                    e.printStackTrace();
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
