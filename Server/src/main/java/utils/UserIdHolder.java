package utils;

public class UserIdHolder {
    private static ThreadLocal<Integer> userIdHolder = new ThreadLocal<>();

    public static void setUserId(int userId) {
        userIdHolder.set(userId);
    }

    public static Integer getUserId() {
        return userIdHolder.get();
    }

    public static void clearUserId() {
        userIdHolder.remove();
    }
}
