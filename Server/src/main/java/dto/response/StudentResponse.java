package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentResponse {
    private int id;
    private String studentId;
    private String email;
    private String name;
    private String phone;
}
