package dto.request;

import lombok.*;

@Getter
@Setter
@Builder
public class UserRegisterRequest {
    private String username;
    private String email;
    private String name;
    private String phone;
}
