package repository;

import dto.response.GuestResponse;
import model.Appointment;
import model.GroupMeeting;
import model.Notification;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GroupMeetingRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public GroupMeeting create(GroupMeeting groupMeeting) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(groupMeeting);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return groupMeeting;
    }

    public List<GuestResponse> findByAppointmentId(int appointmentId) {
        List<GuestResponse> results = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query<Object[]> query = session.createQuery("SELECT gm.user.id, gm.user.name FROM GroupMeeting gm WHERE gm.id.appointmentId = :appointmentId", Object[].class);
            query.setParameter("appointmentId", appointmentId);
            List<Object[]> queryResults = query.getResultList();
            for (Object[] row : queryResults) {
                int userId = (int) row[0];
                String name = (String) row[1];
                results.add(new GuestResponse(userId, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<GroupMeeting> findByUserId(int userId) {
        List<GroupMeeting> results = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT gm FROM GroupMeeting gm WHERE gm.user.id = :userId AND gm.appointment.deleteTime IS NULL", GroupMeeting.class);
            query.setParameter("userId", userId);
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }



}
