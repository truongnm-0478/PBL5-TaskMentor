package service;

import model.Appointment;
import model.GroupMeeting;
import model.User;
import repository.GroupMeetingRepository;

import java.util.List;
import java.util.Set;

public class GroupMeetingService {

    private final GroupMeetingRepository groupMeetingRepository = new GroupMeetingRepository();

    public void createGroupMeeting(Appointment appointment, List<Integer> listGuest) {
        for (Integer guestId : listGuest) {
            GroupMeeting.GroupMeetingId groupMeetingId = new GroupMeeting.GroupMeetingId(appointment.getId(), guestId);
            GroupMeeting groupMeeting = GroupMeeting.builder()
                    .id(groupMeetingId)
                    .appointment(appointment)
                    .user(User.builder().id(guestId).build())
                    .build();
            groupMeetingRepository.create(groupMeeting);
        }
    }
}
