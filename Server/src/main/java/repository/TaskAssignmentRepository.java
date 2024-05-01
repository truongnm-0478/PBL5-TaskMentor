package repository;

import model.Task;
import model.TaskAssignment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;


public class TaskAssignmentRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public TaskAssignment save(TaskAssignment taskAssignment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(taskAssignment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return taskAssignment;
    }

    public TaskAssignment findByTaskId(int taskId) {
        try (Session session = sessionFactory.openSession()) {
            Query<TaskAssignment> query = session.createQuery("FROM TaskAssignment WHERE task.id = :taskId", TaskAssignment.class);
            query.setParameter("taskId", taskId);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TaskAssignment update(TaskAssignment task) {
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

    public List<TaskAssignment> findByTeamId(int teamId) {
        List<TaskAssignment> sprintList  = null;
        try (Session session = sessionFactory.openSession()) {
            Query<TaskAssignment> query = session.createQuery("FROM TaskAssignment t WHERE t.task.sprint.planning.team.id = :teamId AND deleteTime IS NULL AND t.sprint.planning.deleteTime IS NULL ORDER BY t.insertTime ASC", TaskAssignment.class);
            query.setParameter("teamId", teamId);
            sprintList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprintList;
    }
}
