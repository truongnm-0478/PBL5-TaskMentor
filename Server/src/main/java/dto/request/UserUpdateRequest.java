package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserUpdateRequest {
    private int id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private int role;
}
