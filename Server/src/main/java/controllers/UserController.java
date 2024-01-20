package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/user")

public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            // Get list users from database
            List<User> userList = session.createQuery("FROM User", User.class).list();

            // Convert list users to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonUsers = objectMapper.writeValueAsString(userList);

            // Send JSON to client
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonUsers);

        }
    }

}
