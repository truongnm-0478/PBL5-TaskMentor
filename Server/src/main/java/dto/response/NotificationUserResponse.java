package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUserResponse {
    private int id;
    private String classCode;
    private String className;
    private String teacherName;
    private String content;
    private Timestamp insertTime;
    private int insertBy;
}
