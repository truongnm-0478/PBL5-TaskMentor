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
public class CommentResponse {
    private int id;
    private String name;
    private String username;
    private int projectId;
    private String projectTitle;
    private Boolean status;
    private String comment;
    private Timestamp insertTime;
}
