package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.AppointmentRequest;
import dto.response.AppointmentResponse;
import model.Appointment;
import service.AppointmentService;
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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@WebServlet("/api/appointment")
public class AppointmentController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);

                System.out.println("userId = " + userId);

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                List<AppointmentResponse> appointmentList = appointmentService.getAppointmentsByUserId(userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List appointment", appointmentList);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        requestProcessor.processRequest(() -> {
            try {
                int userId = AuthorizationUtil.getUserId(request);

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // parse JSON to AppointmentRequest object
                ObjectMapper mapper = new ObjectMapper();
                AppointmentRequest appointment = mapper.readValue(jsonRequest.toString(), AppointmentRequest.class);


                Appointment appointmentResponse = appointmentService.createAppointment(appointment.getName(), appointment.getDate_start(), appointment.getDate_end(), appointment.getLocation(), appointment.getReminder(), userId, appointment.getList_guest());

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Create appointment successfully.", appointmentResponse);
            } catch (IllegalArgumentException e) {
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
