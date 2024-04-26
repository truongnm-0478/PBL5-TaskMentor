package dto.response;

import lombok.*;
import model.Teacher;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassIntroductionResponse {
    private String code;
    private String name;
    private Teacher teacher;
    private String description;
    private Timestamp createDate;
}
