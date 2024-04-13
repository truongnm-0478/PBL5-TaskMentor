package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dto.response.UserResponse;
import model.User;
import repository.UserRepository;

import java.util.Date;
import java.util.List;

import util.PasswordHashingUtil;
import util.UserValidationUtil;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public List<UserResponse> getAllUsers(int pageNumber, int pageSize) {
        return userRepository.getAllUsers(pageNumber, pageSize);
    }

    public int getTotalUsers() {
        return userRepository.getTotalUsers();
    }

    public UserResponse getUserById(int userId) throws Exception {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            return UserResponse.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .phone(user.getPhone())
                    .deleteTime(user.getDeleteTime())
                    .deleteBy(user.getDeleteBy())
                    .insertTime(user.getInsertTime())
                    .insertBy(user.getInsertBy())
                    .updateTime(user.getUpdateTime())
                    .updateBy(user.getUpdateBy())
                    .build();
        }
        return null;

    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && PasswordHashingUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserByUsername (String username) {
        return userRepository.getUserByUsername(username);
    }

    public User registerUser(String email, String username, String password, int role, String name, String phone) throws JsonProcessingException {
        // Validate information
        if (!UserValidationUtil.isValidRegistrationData(email, username, password, name)) {
            throw new IllegalArgumentException("Missing required fields for user registration.");
        }

        // Validate email format
        if (!UserValidationUtil.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Validate password format
        if (!UserValidationUtil.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password format.");
        }

        // Check username and email
        if (userRepository.getUserByEmail(email) != null || userRepository.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Email or username already exists.");
        }

        // Encrypt password
        String hashedPassword = PasswordHashingUtil.hashPassword(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(hashedPassword)
                .role(role)
                .phone(phone)
                .deleteTime(null)
                .deleteBy(null)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(null)
                .updateTime(null)
                .insertBy(null)
                .build();

        userRepository.save(newUser);

        return newUser;
    }


    public User createTeacherAccount (String email, String username, String name, String phone, int userId) throws JsonProcessingException {
        // Validate information
        if (!UserValidationUtil.isValidTeacherAccountData(email, username, name)) {
            throw new IllegalArgumentException("Missing required fields for user registration.");
        }

        // Validate email format
        if (!UserValidationUtil.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
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
        // Encrypt password
        String hashedPassword = PasswordHashingUtil.hashPassword(password);


        User newUser = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(hashedPassword)
                .role(2)
                .phone(phone)
                .deleteTime(null)
                .deleteBy(null)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .updateTime(null)
                .updateBy(null)
                .build();


        userRepository.save(newUser);

        return newUser;
    }
}
