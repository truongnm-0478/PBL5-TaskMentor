package controllers;

import dto.response.TeamMemberResponse;
import dto.response.TeamResponse;
import model.TeamMember;
import service.TeamService;
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

@WebServlet("/api/teamMember")
public class TeamMemberController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private final TeamService teamService = new TeamService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);

                int id = Integer.parseInt(request.getParameter("id"));

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                List<TeamMemberResponse> teamMemberList = teamService.getTeamMemberByTeamID(id);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List member", teamMemberList);

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
