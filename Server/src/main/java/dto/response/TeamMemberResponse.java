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
public class TeamMemberResponse {
    private int id;
    private String name;
    private String mail;
    private String phone;
    private String username;
    private String studentID;
    private Boolean isLeader;

}
