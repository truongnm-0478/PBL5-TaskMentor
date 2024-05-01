package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.GroupCreateRequest;
import dto.request.PlanRequest;
import dto.request.SprintRequest;
import dto.response.PlanResponse;
import dto.response.RequirementResponse;
import dto.response.StudentResponse;
import model.Planning;
import model.Sprint;
import service.ProjectService;
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

@WebServlet("/api/plan")
public class SprintController extends HttpServlet {

    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final ProjectService projectService = new ProjectService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(1, 0, 2);
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
                PlanRequest planRequest = mapper.readValue(jsonRequest.toString(), PlanRequest.class);

                Planning planning = projectService.createPlanning(planRequest, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Create plan successfully.", planning);
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

                List<PlanResponse> sprintList = projectService.getPlanning(Integer.parseInt(teamId));

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Get planning successfully.", sprintList);
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(1, 0, 2);
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
                SprintRequest sprintRequest = mapper.readValue(jsonRequest.toString(), SprintRequest.class);

                Sprint sprint = projectService.updateSprint(sprintRequest, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Update successfully.", sprint);
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
                List<Integer> requiredRoles = Arrays.asList(1, 0, 2);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);

                int id = Integer.parseInt(request.getParameter("id"));

                Sprint sprint = projectService.deleteSprint(id, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Delete sprint successfully.", sprint);
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
