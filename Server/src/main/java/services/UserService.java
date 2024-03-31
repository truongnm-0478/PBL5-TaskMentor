package services;

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

    public User registerUser(String email, String username, String password, int role, String name, String phone) {
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

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);
        newUser.setName(name);
        newUser.setPhone(phone);
        newUser.setDeleteTime(null);
        newUser.setDeleteBy(null);

        userRepository.save(newUser);

        return newUser;
    }
}
