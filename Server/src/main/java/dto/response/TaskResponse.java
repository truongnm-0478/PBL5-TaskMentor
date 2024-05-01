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
public class TaskResponse {
    private int id;
    private int sprintId;
    private String sprintName;
    private String taskName;
    private int stage;
    private String description;
    private int status;
    private Timestamp insertTime;
    private String userName;
}
