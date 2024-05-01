package dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskItemResponse {
    private int id;
    private int sprintId;
    private String taskName;
    private String description;
    private int status;
}
