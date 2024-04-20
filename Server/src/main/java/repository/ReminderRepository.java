package repository;

import model.Reminder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class ReminderRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Reminder create(Reminder reminder) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reminder);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return reminder;
    }
}
