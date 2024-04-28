package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamStudentResponse {
    private int id;
    private String name;
    private String className;
    private String classCode;
    private int role;
    private Leader leader;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Leader {
        private String studentId;
        private String name;
    }
}
