package utils;

public class UserValidationUtils {

    public static boolean isValidRegistrationData(String email, String username, String password, String name) {
        return isNonEmptyString(email) && isNonEmptyString(username) && isNonEmptyString(password) && isNonEmptyString(name);
    }

    public static boolean isValidEmail(String email) {
        // Validate email format
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    private static boolean isNonEmptyString(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean isValidTeacherAccountData(String email, String username, String name) {
        return isNonEmptyString(email) && isNonEmptyString(username) &&  isNonEmptyString(name);
    }
}
