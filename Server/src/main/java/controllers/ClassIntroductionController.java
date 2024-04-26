package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.ClassRequest;
import dto.response.AppointmentResponse;
import dto.response.ClassIntroductionResponse;
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

@WebServlet("/api/class-introduction")
public class ClassIntroductionController extends HttpServlet {

    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final ClassService classService = new ClassService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);

                String code = request.getParameter("code");

                System.out.println("code V= " + code);

                ClassIntroductionResponse classIntroductionResponse = classService.getIntroduction(code);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Class introduction", classIntroductionResponse);

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
