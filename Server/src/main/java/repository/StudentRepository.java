package repository;

import model.Student;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class StudentRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public boolean isCodeExists(String code) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Student WHERE code = :code", Long.class);
            query.setParameter("code", code);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCodeAndUserIdExists(int userId, String code) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Student WHERE user.id = :userId AND code = :code", Long.class);
            query.setParameter("userId", userId);
            query.setParameter("code", code);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserIdExists(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Student WHERE user.id = :userId", Long.class);
            query.setParameter("userId", userId);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(student);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(student);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getStudentByCodeAndUserId(int userId, String code) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("FROM Student WHERE user.id = :userId AND code = :code", Student.class);
            query.setParameter("userId", userId);
            query.setParameter("code", code);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student getStudentByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("FROM Student WHERE user.id = :userId", Student.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
