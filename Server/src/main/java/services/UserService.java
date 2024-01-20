package services;

import models.User;

public class UserService {

    public User registerUser(String email, String username, String password, String phone) {
        // Thực hiện các kiểm tra hợp lý và xử lý đăng ký người dùng
        // Nếu có lỗi, ném IllegalArgumentException
        validateUserData(email, username, password, phone);

        // Giả sử rằng bạn có một hàm để lưu trữ người dùng vào cơ sở dữ liệu
        // (trong thực tế, bạn sẽ cần thêm các bước như kết nối cơ sở dữ liệu)
        // int generatedId = saveUserToDatabase(email, username, password, phone);

        // Trả về đối tượng User mới tạo
        return new User.UserBuilder()
                .setEmail(email)
                .setUsername(username)
                .setPassword(password)
                .setPhone(phone)
                .build();
    }

    private void validateUserData(String email, String username, String password, String phone) {
        // Thực hiện các kiểm tra hợp lý, ví dụ:
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        // Các điều kiện khác...

        // Có thể thêm các kiểm tra phức tạp khác tùy thuộc vào yêu cầu của bạn
    }

    // private int saveUserToDatabase(String email, String username, String password, String phone) {
    //     // Giả sử rằng bạn có một hàm để lưu trữ người dùng vào cơ sở dữ liệu và trả về id được sinh ra
    //     // Trong thực tế, bạn sẽ thêm các bước kết nối và thao tác với cơ sở dữ liệu
    //     // Đây chỉ là một giả định đơn giản
    //     // ...

    //     // Giả sử rằng id được sinh ra từ cơ sở dữ liệu
    //     // int generatedId = // ...;

    //     // return generatedId;
    // }
}
