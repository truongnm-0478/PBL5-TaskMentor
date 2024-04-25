package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.ClassRequest;
import dto.request.JoinClassRequest;
import model.ClassRoom;
import service.ClassService;
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

@WebServlet("/api/joinClass")
public class JoinClassController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final ClassService classService = new ClassService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);
                System.out.println("userId JOIN CLASS = " + userId);

                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // parse JSON to User object
                ObjectMapper mapper = new ObjectMapper();
                JoinClassRequest joinClassRequest = mapper.readValue(jsonRequest.toString(), JoinClassRequest.class);

                Boolean isJoin = classService.joinClass(joinClassRequest, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Class created successfully.", isJoin);
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
