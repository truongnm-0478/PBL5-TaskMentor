package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendJsonResponse(HttpServletResponse response, Object responseObject) throws IOException {
        String jsonResponse = convertObjectToJson(responseObject);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private static String convertObjectToJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }
}
