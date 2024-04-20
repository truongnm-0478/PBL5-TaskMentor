package repository;

import dto.response.AppointmentResponse;
import model.Appointment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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

    public List<AppointmentResponse> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Appointment> query = session.createQuery("SELECT a FROM Appointment a WHERE a.user.id = :userId", Appointment.class);
            query.setParameter("userId", userId);
            List<Appointment> appointments = query.getResultList();
            List<AppointmentResponse> appointmentResponses = new ArrayList<>();
            for (Appointment appointment : appointments) {
                AppointmentResponse response = new AppointmentResponse();
                response.setId(appointment.getId());
                response.setStartDate(appointment.getDateStart());
                response.setEndDate(appointment.getDateEnd());
                response.setName(appointment.getName());
                response.setLocation(appointment.getLocation());
                appointmentResponses.add(response);
            }
            return appointmentResponses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
