package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.PlanRequest;
import dto.request.StatusRequest;
import dto.request.TaskRequest;
import dto.response.TaskResponse;
import model.Planning;
import model.Task;
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

@WebServlet("/api/task")
public class TaskController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final ProjectService projectService = new ProjectService();
    private static final TaskService taskService = new TaskService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
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
                TaskRequest taskRequest = mapper.readValue(jsonRequest.toString(), TaskRequest.class);

                Task task = taskService.saveTask(taskRequest, userId);


                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Create task successfully.", task);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(1, 0, 2);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                String teamId = request.getParameter("id");

                List<TaskResponse> listTask = taskService.getListTask(Integer.parseInt(teamId));


                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Get list task successfully.", listTask);
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
                System.out.println("userId PUT = " + userId);

                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                StatusRequest statusRequest = mapper.readValue(jsonRequest.toString(), StatusRequest.class);

                System.out.println("statusRequest = " + statusRequest);

                Task task = taskService.updateStatus(statusRequest, userId);


                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Update status successfully.", null);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);

                String taskId = request.getParameter("id");

                Boolean isDelete = taskService.deleteTask(Integer.parseInt(taskId), userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Delete successfully.", isDelete);
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
