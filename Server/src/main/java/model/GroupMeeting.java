package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_meeting")
public class GroupMeeting {
    @EmbeddedId
    private GroupMeetingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("appointmentId")
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupMeetingId implements Serializable {
        private int appointmentId;
        private int userId;
    }
}
