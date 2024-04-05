package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import services.UserService;
import utils.RequestProcessor;
import utils.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/api/register")
public class RegisterController extends HttpServlet {
    private final UserService userService = new UserService();
    private final RequestProcessor requestProcessor = new RequestProcessor();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // parse JSON to User object
                ObjectMapper mapper = new ObjectMapper();
                User user = mapper.readValue(jsonRequest.toString(), User.class);

                // register user
                User registeredUser = userService.registerUser(user.getEmail(), user.getUsername(), user.getPassword(), user.getRole(), user.getName(), user.getPhone());

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "User registered successfully.", registeredUser);
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
