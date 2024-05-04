package service;

import dto.response.AppointmentResponse;
import model.Appointment;
import model.ClassRoom;
import model.GroupMeeting;
import model.User;
import repository.AppointmentRepository;
import repository.GroupMeetingRepository;
import repository.ReminderRepository;
import repository.UserRepository;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentService {

    private final UserRepository userRepository = new UserRepository();

    private final AppointmentRepository appointmentRepository = new AppointmentRepository();
    private final ReminderService reminderService = new ReminderService();
    private final GroupMeetingService groupMeetingService = new GroupMeetingService();
    private final GroupMeetingRepository groupMeetingRepository = new GroupMeetingRepository();
    private final ReminderRepository reminderRepository = new ReminderRepository();
    public AppointmentService() {}

    public List<AppointmentResponse> getAppointmentsByUserId(int userId) {
        List<Appointment> appointmentList = appointmentRepository.findByUserId(userId);
        List<AppointmentResponse> appointmentResponsesList = new ArrayList<>();

        int role = userRepository.getUserById(userId).getRole();

        for(Appointment a : appointmentList) {
            Timestamp timeBefore = reminderRepository.findRemindersByAppointmentId(a.getId()).get(0).getTimeBefore();
            Timestamp startDate = a.getDateStart();

            Duration duration = Duration.between(timeBefore.toInstant(), startDate.toInstant());

            int amount;
            String unit;

            if (duration.toMinutes() % 60 == 0 && duration.toHours() % 24 == 0) {
                amount = (int) duration.toDays();
                unit = "Days";
            } else if (duration.toMinutes() % 60 == 0) {
                amount = (int) duration.toHours();
                unit = "Hours";
            } else {
                amount = (int) duration.toMinutes();
                unit = "Minutes";
            }


            AppointmentResponse appointmentResponse = AppointmentResponse.builder()
                    .id(a.getId())
                    .start(a.getDateStart())
                    .end(a.getDateEnd())
                    .title(a.getName())
                    .location(a.getLocation())
                    .timeBefore(timeBefore)
                    .listGuest(groupMeetingRepository.findByAppointmentId(a.getId()))
                    .reminder(amount)
                    .typeTime(unit)
                    .color((role == 2) ? "important" : "student")
                    .build();
            appointmentResponsesList.add(appointmentResponse);
        }

        return appointmentResponsesList;
    }

    public List<AppointmentResponse> getAppointmentOfGuest(int userId) {
        List<GroupMeeting> groupMeetingList = groupMeetingRepository.findByUserId(userId);

        List<AppointmentResponse> appointmentResponsesList = new ArrayList<>();


        for(GroupMeeting a : groupMeetingList) {
            Timestamp timeBefore = reminderRepository.findRemindersByAppointmentId(a.getAppointment().getId()).get(0).getTimeBefore();
            Timestamp startDate = a.getAppointment().getDateStart();

            Duration duration = Duration.between(timeBefore.toInstant(), startDate.toInstant());

            int amount;
            String unit;

            if (duration.toMinutes() % 60 == 0 && duration.toHours() % 24 == 0) {
                amount = (int) duration.toDays();
                unit = "Days";
            } else if (duration.toMinutes() % 60 == 0) {
                amount = (int) duration.toHours();
                unit = "Hours";
            } else {
                amount = (int) duration.toMinutes();
                unit = "Minutes";
            }


            AppointmentResponse appointmentResponse = AppointmentResponse.builder()
                    .id(a.getAppointment().getId())
                    .start(a.getAppointment().getDateStart())
                    .end(a.getAppointment().getDateEnd())
                    .title(a.getAppointment().getName())
                    .location(a.getAppointment().getLocation())
                    .timeBefore(timeBefore)
                    .listGuest(groupMeetingRepository.findByAppointmentId(a.getAppointment().getId()))
                    .reminder(amount)
                    .typeTime(unit)
                    .color((a.getAppointment().getUser().getRole() == 2) ? "important" : "student")
                    .build();
            appointmentResponsesList.add(appointmentResponse);
        }

        return appointmentResponsesList;
    }


    public Appointment createAppointment(String name, Timestamp dateStart, Timestamp dateEnd, String location, String reminder, int userId, List<Integer> listGuest) {
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

    public Appointment updateAppointment(int id, String name, Timestamp dateStart, Timestamp dateEnd, String location, String reminder, int userId, List<Integer> listGuest) {
        User user = User.builder().id(userId).build();
        Appointment appointment = appointmentRepository.findById(id);
        appointment.setDateStart(dateStart);
        appointment.setDateEnd(dateEnd);
        appointment.setLocation(location);
        appointment.setUpdateBy(userId);
        appointment.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));
        appointment.setName(name);


        Appointment updateAppointment = appointmentRepository.update(appointment);

        return updateAppointment;
    }

    public boolean removeAppointment(int id, int userId) {
        try {
            Appointment appointment = appointmentRepository.findById(id);
            appointment.setDeleteBy(userId);
            appointment.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
            appointmentRepository.update(appointment);
            return true;
        } catch (Exception e) {
            return false;
        }
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

