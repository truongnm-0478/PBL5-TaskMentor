package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamEvaluationResponse {
    private String studentName;
    private float averageDifficulty;
    private int totalTasks;
    private String studentId;
}
