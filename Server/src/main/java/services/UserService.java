package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import repositories.UserRepository;
import java.util.List;
import utils.PasswordHashingUtils;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && PasswordHashingUtils.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserByUsername (String username) {
        return userRepository.getUserByUsername(username);
    }

    public User registerUser(String email, String username, String password, int role, String name, String phone) throws JsonProcessingException {
        // Validate information
        if (email == null || email.isEmpty() || username == null || username.isEmpty() ||
                password == null || password.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Missing required fields for user registration.");
        }

        // Check username and email
        if (userRepository.getUserByEmail(email) != null || userRepository.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Email or username already exists.");
        }

        // Encrypt password
        String hashedPassword = PasswordHashingUtils.hashPassword(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(hashedPassword)
                .role(role)
                .phone(phone)
                .deleteTime(null)
                .deleteBy(null)
                .build();

        userRepository.save(newUser);

        return newUser;
    }

    public User createTeacherAccount (String email, String username, String name, String phone) throws JsonProcessingException {
        // Validate information
        if (email == null || email.isEmpty() || username == null || username.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Missing required fields for user registration.");
        }

        // Check username and email
        if (userRepository.getUserByEmail(email) != null || userRepository.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Email or username already exists.");
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Create password form email
        String password = email.substring(0, atIndex);
        System.out.printf("Password: " + password);
        // Encrypt password
        String hashedPassword = PasswordHashingUtils.hashPassword(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(hashedPassword)
                .role(3)
                .phone(phone)
                .deleteTime(null)
                .deleteBy(null)
                .build();

        userRepository.save(newUser);

        return newUser;
    }
}
