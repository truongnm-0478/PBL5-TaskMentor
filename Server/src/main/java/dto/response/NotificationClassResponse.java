package dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
public class NotificationClassResponse {
    private int id;
    private String content;
    private String username;
    private String name;
    private Timestamp dateTime;
}
