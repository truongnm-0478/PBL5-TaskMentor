package repository;

import dto.response.AppointmentResponse;
import model.Appointment;
import model.StudentClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Appointment findById(int appointmentId) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Appointment.class, appointmentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Appointment save(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(appointment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return appointment;
    }

    public List<Appointment> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Appointment> query = session.createQuery("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.deleteTime IS NULL", Appointment.class);
            query.setParameter("userId", userId);
            List<Appointment> appointments = query.getResultList();
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Appointment update(Appointment appointment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(appointment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return appointment;
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Appointment appointment = session.get(Appointment.class, id);

            if (appointment != null) {
                session.delete(appointment);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
