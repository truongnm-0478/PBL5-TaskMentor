package repository;

import model.Appointment;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class TeacherRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Teacher getByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Teacher WHERE user.id = :userId");
            query.setParameter("userId", userId);
            return (Teacher) query.uniqueResult();
        }
    }

    public Teacher save(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Teacher> query = session.createQuery("FROM Teacher WHERE deleteTime IS NULL", Teacher.class);
            return query.list();
        }
    }
}
