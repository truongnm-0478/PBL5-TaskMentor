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
public class TaskDifficultyResponse {
    private String studentId;
    private int status;
    private String userName;
    private float difficulty;
}
