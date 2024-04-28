package repository;

import model.Notification;
import model.Project;
import model.Reminder;
import model.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ProjectRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveProject(Project project) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Project> findProjectByTeamId(int teamId) {
        List<Project> projects = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Project> query = session.createQuery("FROM Project p WHERE p.team.id = :teamId AND deleteTime IS NULL", Project.class);
            query.setParameter("teamId", teamId);
            projects = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }

    public Project update(Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return project;
    }

    public Project getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Project.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
