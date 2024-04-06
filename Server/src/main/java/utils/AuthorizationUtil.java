package utils;

import models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationUtil {

    public static boolean checkUserRole(User currentUser, int roleToCheck) {
        if (currentUser == null) {
            return false;
        }

        int userRole = currentUser.getRole();
        return userRole != -1 && userRole == roleToCheck;
    }

    public static int getUserId(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        return currentUser.getId();
    }

    public static int getUserRole(User currentUser) {
        if (currentUser == null) {
            return -1;
        }

        return currentUser.getRole();
    }

    public static boolean checkUserRole(HttpServletRequest request, HttpServletResponse response, int requiredRole) throws IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != requiredRole) {
            ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied.");
            return false;
        }
        return true;
    }
}
