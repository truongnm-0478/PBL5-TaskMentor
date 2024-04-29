package repository;

import model.ProjectApproval;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

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

}
