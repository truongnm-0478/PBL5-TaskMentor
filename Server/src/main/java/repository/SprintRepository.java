package repository;

import model.Planning;
import model.ProjectApproval;
import model.Sprint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class SprintRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Sprint create(Sprint sprint) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(sprint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sprint;
    }

    public List<Sprint> findByTeamId(int teamId) {
        List<Sprint> sprintList  = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Sprint> query = session.createQuery("FROM Sprint p WHERE p.planning.team.id = :teamId AND deleteTime IS NULL AND p.planning.deleteTime IS NULL ORDER BY p.stage ASC", Sprint.class);
            query.setParameter("teamId", teamId);
            sprintList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprintList;
    }

    public Sprint getById(int sprintId) {
        Sprint sprint = null;
        try (Session session = sessionFactory.openSession()) {
            sprint = session.get(Sprint.class, sprintId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprint;
    }

    public Sprint update(Sprint sprint) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(sprint);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sprint;
    }

}
