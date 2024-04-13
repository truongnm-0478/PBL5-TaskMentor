package dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponse {
    private int id;
    private String email;
    private String username;
    private int role;
    private String name;
    private String phone;
    private Date deleteTime;
    private Integer deleteBy;
    private Date insertTime;
    private Integer insertBy;
    private Date updateTime;
    private Integer updateBy;

    public UserResponse(int id, String email, String username, int role, String name, String phone, Date deleteTime, Integer deleteBy, Date insertTime, Integer insertBy, Date updateTime, Integer updateBy) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.name = name;
        this.phone = phone;
        this.deleteTime = deleteTime;
        this.deleteBy = deleteBy;
        this.insertTime = insertTime;
        this.insertBy = insertBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
    }
}
