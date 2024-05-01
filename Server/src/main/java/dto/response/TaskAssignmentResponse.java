package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignmentResponse {
    private int id;
    private int taskId;
    private int sprintId;
    private String taskName;
    private String sprintName;
    private int stage;
    private String description;
    private int status;
    private Timestamp deadline;
    private int assignedTo;
    private List<AssignBy> listAssigned;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class AssignBy {
        private int userId;
        private String name;
    }
}
