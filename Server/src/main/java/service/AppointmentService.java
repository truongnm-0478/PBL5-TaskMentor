package service;

import dto.response.AppointmentResponse;
import model.Appointment;
import model.GroupMeeting;
import model.User;
import repository.AppointmentRepository;
import repository.GroupMeetingRepository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final ReminderService reminderService = new ReminderService();
    private final GroupMeetingService groupMeetingService = new GroupMeetingService();
    private final GroupMeetingRepository groupMeetingRepository = new GroupMeetingRepository();
    public AppointmentService() {}

    public List<AppointmentResponse> getAppointmentsByUserId(int userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public Appointment createAppointment(String name, Timestamp dateStart, Timestamp dateEnd, String location, String reminder, int userId, List<Integer> listGuest) {
        System.out.println("APP SER");
        User user = User.builder().id(userId).build();
        Appointment appointment = Appointment.builder()
                .name(name)
                .dateStart(dateStart)
                .dateEnd(dateEnd)
                .location(location)
                .user(user)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Parse reminder string and create reminder
        parseAndCreateReminder(savedAppointment, reminder, userId);

        // Create GroupMeeting for each guest
        createGroupMeetingsForGuests(savedAppointment, listGuest);

        return savedAppointment;
    }

    private void createGroupMeetingsForGuests(Appointment appointment, List<Integer> listGuest) {
        groupMeetingService.createGroupMeeting(appointment, listGuest);
    }

    private void parseAndCreateReminder(Appointment appointment, String reminder, int userId) {
        // Split reminder string into components
        String[] parts = reminder.split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid reminder format");
        }

        // Parse numeric part
        int amount = Integer.parseInt(parts[0]);

        // Parse time unit part
        String timeUnit = parts[1].toLowerCase(); // Convert to lowercase
        switch (timeUnit.toLowerCase()) {
            case "hours":
                createReminderForHours(appointment, amount, userId);
                break;
            case "days":
                createReminderForDays(appointment, amount, userId);
                break;
            case "minutes":
                createReminderForMinutes(appointment, amount, userId);
                break;
            default:
                throw new IllegalArgumentException("Invalid time unit: " + timeUnit);
        }
    }

    private void createReminderForHours(Appointment appointment, int hours, int userId) {
        Timestamp reminderTime = new Timestamp(appointment.getDateStart().getTime() - hours * 3600 * 1000);
        reminderService.createReminder(appointment, reminderTime, userId);
    }

    private void createReminderForDays(Appointment appointment, int days, int userId) {
        Timestamp reminderTime = new Timestamp(appointment.getDateStart().getTime() - days * 24 * 3600 * 1000);
        reminderService.createReminder(appointment, reminderTime, userId);
    }

    private void createReminderForMinutes(Appointment appointment, int minutes, int userId) {
        Timestamp reminderTime = new Timestamp(appointment.getDateStart().getTime() - minutes * 60 * 1000);
        reminderService.createReminder(appointment, reminderTime, userId);
    }
}
