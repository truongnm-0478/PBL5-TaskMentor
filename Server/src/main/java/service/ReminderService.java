package service;

import model.Appointment;
import model.Reminder;
import repository.ReminderRepository;

import java.sql.Timestamp;
import java.util.Date;

public class ReminderService {

    private final ReminderRepository reminderRepository = new ReminderRepository();


    public Reminder createReminder(Appointment appointment, Timestamp timeBefore, int userId) {
        Reminder reminder = Reminder.builder()
                .appointment(appointment)
                .timeBefore(timeBefore)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .updateTime(null)
                .updateBy(null)
                .deleteBy(null)
                .deleteTime(null)
                .build();

        return reminderRepository.create(reminder);
    }
}
