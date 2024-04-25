package repository;

import model.Reminder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReminderRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Reminder create(Reminder reminder) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reminder);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return reminder;
    }

    public List<Reminder> findRemindersByAppointmentId(int appointmentId) {
        List<Reminder> reminders = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Reminder> query = session.createQuery("FROM Reminder r WHERE r.appointment.id = :appointmentId", Reminder.class);
            query.setParameter("appointmentId", appointmentId);
            reminders = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reminders;
    }

}
