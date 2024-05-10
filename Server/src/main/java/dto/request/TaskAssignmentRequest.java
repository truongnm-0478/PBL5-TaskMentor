package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignmentRequest {
    private int id;
    private int taskId;
    private String name;
    private String description;
    private Timestamp deadline;
    private int assignedTo;
    private int sprint;
    private String proof;
}
