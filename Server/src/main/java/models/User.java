package models;

public class User {

    private int id;
    private String email;
    private String username;
    private String password;
    private int role;
    private String phone;

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
        this.phone = builder.phone;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Builder class
    public static class UserBuilder {
        private int id;
        private String email;
        private String username;
        private String password;
        private int role = 0;
        private String phone;

        public UserBuilder() {
        }

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRole(int role) {
            this.role = role;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            validateUserData(this);
            return new User(this);
        }

        private void validateUserData(UserBuilder builder) {
            if (builder.email == null || builder.email.isEmpty()) {
                throw new IllegalArgumentException("Email is required.");
            }
        }
    }
}
