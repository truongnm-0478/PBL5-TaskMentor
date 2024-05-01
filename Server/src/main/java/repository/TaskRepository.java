package repository;

import model.Sprint;
import model.Task;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class TaskRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Task save(Task task) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return task;
    }

    public List<Task> findByTeamId(int teamId) {
        List<Task> sprintList  = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("FROM Task t WHERE t.sprint.planning.team.id = :teamId AND deleteTime IS NULL AND t.sprint.planning.deleteTime IS NULL ORDER BY t.insertTime ASC", Task.class);
            query.setParameter("teamId", teamId);
            sprintList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprintList;
    }

    public Task update(Task task) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return task;
    }

    public Task findById(int taskId) {
        Task task = null;
        try (Session session = sessionFactory.openSession()) {
            task = session.get(Task.class, taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }


}
