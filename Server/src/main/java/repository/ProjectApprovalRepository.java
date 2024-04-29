package repository;

import model.Project;
import model.ProjectApproval;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ProjectApprovalRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(ProjectApproval projectApproval) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(projectApproval);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProjectApproval> findByTeamId(int teamId) {
        List<ProjectApproval> projectApprovals = null;
        try (Session session = sessionFactory.openSession()) {
            Query<ProjectApproval> query = session.createQuery("FROM ProjectApproval p WHERE p.project.team.id = :teamId AND deleteTime IS NULL AND p.project.deleteTime IS NULL ORDER BY p.insertTime DESC", ProjectApproval.class);
            query.setParameter("teamId", teamId);
            projectApprovals = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectApprovals;
    }

    public ProjectApproval update(ProjectApproval projectApproval) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(projectApproval);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return projectApproval;
    }

    public ProjectApproval getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ProjectApproval.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
