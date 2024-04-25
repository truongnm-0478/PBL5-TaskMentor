package repository;

import dto.request.ClassRequest;
import model.Appointment;
import model.ClassRoom;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ClassRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public ClassRoom save(ClassRoom classRoom) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(classRoom);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return classRoom;
    }

    public List<ClassRoom> findByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT c FROM ClassRoom c WHERE c.insertBy = :userId AND c.deleteTime IS NULL ORDER BY c.insertTime DESC";
            Query<ClassRoom> query = session.createQuery(hql, ClassRoom.class);
            query.setParameter("userId", userId);
            List<ClassRoom> classRequests = query.getResultList();
            return classRequests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ClassRoom findByCode(String code) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT c FROM ClassRoom c WHERE c.code = :code";
            Query<ClassRoom> query = session.createQuery(hql, ClassRoom.class);
            query.setParameter("code", code);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ClassRoom update(ClassRoom classRoom) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(classRoom);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return classRoom;
    }
}
