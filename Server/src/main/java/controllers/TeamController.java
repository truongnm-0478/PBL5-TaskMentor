package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.GroupCreateRequest;
import dto.request.JoinClassRequest;
import dto.response.StudentResponse;
import dto.response.TeamResponse;
import model.Team;
import service.ClassService;
import service.TeamService;
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

@WebServlet("/api/team")
public class TeamController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private final TeamService teamService = new TeamService();

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

                // parse JSON to User object
                ObjectMapper mapper = new ObjectMapper();
                GroupCreateRequest groupCreateRequest = mapper.readValue(jsonRequest.toString(), GroupCreateRequest.class);

                Boolean isSave = teamService.saveTeam(groupCreateRequest, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Create team successfully.", isSave);
            } catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            } catch (IOException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    ex.printStackTrace();
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

                String code = request.getParameter("code");

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                List<TeamResponse> teamList = teamService.getListTeamByClassCode(code);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List team", teamList);

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
            try{
                int userId = AuthorizationUtil.getUserId(request);

                int id = Integer.parseInt(request.getParameter("id"));

                Boolean isDelete = teamService.deleteTeam(id, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Delete successfully", isDelete);

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
}
