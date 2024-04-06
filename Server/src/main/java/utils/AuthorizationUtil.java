package utils;

import models.User;

public class AuthorizationUtil {

    public static boolean checkUserRole(User currentUser, int roleToCheck) {
        if (currentUser == null ) {
            return false;
        }

        int userRole = currentUser.getRole();
        return userRole != -1 && userRole == roleToCheck;
    }

    public static int getUserId(User currentUser) {
        if (currentUser == null) {
            return -1;
        }

        return currentUser.getId();
    }

    public static int getUserRole(User currentUser) {
        if (currentUser == null) {
            return -1;
        }

        return currentUser.getRole();
    }
}
