package repository;

import model.Planning;
import model.ProjectApproval;
import model.Reminder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class PlanningRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Planning create(Planning planning) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(planning);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return planning;
    }
}
