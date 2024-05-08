package controllers;

import dto.response.TaskDifficultyResponse;
import dto.response.TaskResponse;
import service.ProjectService;
import service.TaskService;
import util.AuthorizationUtil;
import util.RequestProcessor;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/api/team-evaluation")
public class TeamEvaluationController extends HttpServlet {
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

                String teamId = request.getParameter("id");

                List<TaskDifficultyResponse> listTask = taskService.evaluateTaskDifficulty(Integer.parseInt(teamId));

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
}

