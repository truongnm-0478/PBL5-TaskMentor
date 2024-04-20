package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserInfoResponse {
    private int id;
    private String email;
    private String username;
    private int role;
    private String name;
    private String phone;
}
