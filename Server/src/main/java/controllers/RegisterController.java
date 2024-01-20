package controllers;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;

@WebServlet("/api/register")
public class RegisterController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Tạo một đối tượng User để chuyển đổi thành JSON
//        User user = new User.UserBuilder()
//                    .setId(1)
//                    .setEmail("example@gmail.com")
//                    .setUsername("exampleUser")
//                    .setPassword("password123")
//                    .setPhone("123456789")
//                    .build();
//
//        // Chuyển đối tượng thành chuỗi JSON
//        String jsonResponse = convertObjectToJson(user);
//
//        // Thiết lập kiểu nội dung là JSON
//        response.setContentType("application/json");
//
//        // Gửi chuỗi JSON về client
//        response.getWriter().write(jsonResponse);
    }

    private String convertObjectToJson(Object object) throws IOException {
        // Sử dụng ObjectMapper để chuyển đối tượng thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
