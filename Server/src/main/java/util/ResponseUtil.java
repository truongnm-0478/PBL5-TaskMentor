package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.UserResponse;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendJsonResponse(HttpServletResponse response, int statusCode, String message, Object responseData) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(new JsonResponse(statusCode, message, responseData)));
        out.flush();
    }

    public static void sendPagedJsonResponse(HttpServletResponse response, int statusCode, String message, int page, int pageSize, int totalItems, int totalPages, List<UserResponse> users) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page", page);
        responseData.put("pageSize", pageSize);
        responseData.put("totalItems", totalItems);
        responseData.put("totalPages", totalPages);
        responseData.put("users", users);

        sendJsonResponse(response, statusCode, message, responseData);
    }


    public static void sendErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(new JsonResponse(statusCode, errorMessage, null)));
        out.flush();
    }

    @Getter
    private static class JsonResponse {
        private int status;
        private String message;
        private Object data;

        public JsonResponse(int status, String message, Object data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

    }
}
