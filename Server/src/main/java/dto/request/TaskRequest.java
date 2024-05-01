package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskRequest {
    private int sprint;
    private Timestamp deadline;
    private String description;
    private String name;
    private String status;
}
