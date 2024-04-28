package dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupCreateRequest {
    private String group;
    private List<Member> members;
    private String code;
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Member {
        private String studentId;
        private boolean leader;
    }
}
