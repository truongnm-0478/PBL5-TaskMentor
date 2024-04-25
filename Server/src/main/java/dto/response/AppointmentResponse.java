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
public class AppointmentResponse {
    private int id;
    private Timestamp start;
    private Timestamp end;
    private String title;
    private String location;
    private List<GuestResponse> listGuest;
    private Timestamp timeBefore;
    private int reminder;
    private String typeTime;
    private String color;
}
