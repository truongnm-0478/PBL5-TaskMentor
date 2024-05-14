package service;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import dto.request.ChangePasswordRequest;
import dto.request.UserUpdateRequest;
import dto.response.UserInfoResponse;
import dto.response.UserResponse;
import model.Teacher;
import model.User;
import repository.TeacherRepository;
import repository.UserRepository;
import util.PasswordHashingUtil;
import util.UserValidationUtil;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final TeacherRepository teacherRepository = new TeacherRepository();

    // Get list user for admin role
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUsersAdmin();
    }

    public List<UserInfoResponse> getAllUserInfo() {
        return userRepository.getAllUsers();
    }

    public int getTotalUsers() {
        return userRepository.getTotalUsers();
    }

    public User updateUser(UserUpdateRequest userUpdateRequest, int userId) {
        // Lấy thông tin người dùng hiện tại từ cơ sở dữ liệu
        User currentUser = userRepository.getUserById(userUpdateRequest.getId());

        // Kiểm tra email mới có trùng với người dùng khác trong hệ thống không
        User userByEmail = userRepository.getUserByEmail(userUpdateRequest.getEmail());
        if (userByEmail != null && userByEmail.getId() != userUpdateRequest.getId()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Kiểm tra tên người dùng mới có trùng với người dùng khác trong hệ thống không
        User userByUsername = userRepository.getUserByUsername(userUpdateRequest.getUsername());
        if (userByUsername != null && userByUsername.getId() != userUpdateRequest.getId()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Cập nhật thông tin người dùng
        currentUser.setEmail(userUpdateRequest.getEmail());
        currentUser.setPhone(userUpdateRequest.getPhone());
        currentUser.setRole(userUpdateRequest.getRole());
        currentUser.setUpdateBy(userId);
        currentUser.setName(userUpdateRequest.getName());
        currentUser.setUsername(userUpdateRequest.getUsername());
        currentUser.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));

        // Lưu thông tin người dùng đã cập nhật vào cơ sở dữ liệu
        return userRepository.update(currentUser);
    }

    public Boolean deleteUser(int id, int userId) {
        User user = userRepository.getUserById(id);
        if (user.getDeleteTime() == null) {
            user.setDeleteBy(userId);
            user.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        } else {
            user.setDeleteBy(null);
            user.setDeleteTime(null);
        }

        User userResult = userRepository.update(user);
        return true;
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
                    .id(user.getId())
                    .build();
        }
        return null;

    }

    // đăng nhập
    public User authenticateUser(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user.getDeleteTime() != null) {
            return null;
        }
        if (user != null && PasswordHashingUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
    // dang kí
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


    // Add user account
    public User createTeacherAccount(String email, String username, String name, String phone, int userId) throws JsonProcessingException {
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
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();

        User user = userRepository.save(newUser);

        if (user != null) {
            Teacher teacher = Teacher.builder()
                    .user(newUser)
                    .insertTime(new java.sql.Timestamp(new Date().getTime()))
                    .insertBy(userId)
                    .build();
            teacherRepository.save(teacher);
        }

        return newUser;
    }

    // Change password
    public User changePassword(ChangePasswordRequest changePasswordRequest, int userId) {
        User user = userRepository.getUserById(userId);
        if (user != null && PasswordHashingUtil.verifyPassword(changePasswordRequest.getCurrent(), user.getPassword())) {
            String hashedPassword = PasswordHashingUtil.hashPassword(changePasswordRequest.getPass());
            System.out.println("password = " + hashedPassword);
            System.out.println("hashedPassword = " + hashedPassword);
            user.setPassword(hashedPassword);
            userRepository.update(user);
            return user;
        }
        return null;
    }
}
