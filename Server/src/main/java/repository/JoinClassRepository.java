package repository;

import model.Student;
import model.StudentClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class JoinClassRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void addStudentToClass(StudentClass studentClass) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(studentClass);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStudentInClass(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM StudentClass WHERE student.id = :studentId", Long.class);
            query.setParameter("studentId", student.getId());
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StudentClass> getClassesByStudentId(int studentId) {
        try (Session session = sessionFactory.openSession()) {
            Query<StudentClass> query = session.createQuery("FROM StudentClass WHERE student.id = :studentId", StudentClass.class);
            query.setParameter("studentId", studentId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentClass> getClassesByClassCode(String classCode) {
        try (Session session = sessionFactory.openSession()) {
            Query<StudentClass> query = session.createQuery("FROM StudentClass sc WHERE sc.classRoom.code = :classCode", StudentClass.class);
            query.setParameter("classCode", classCode);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteStudentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            StudentClass studentClass = session.get(StudentClass.class, id);

            if (studentClass != null) {
                session.delete(studentClass);
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
