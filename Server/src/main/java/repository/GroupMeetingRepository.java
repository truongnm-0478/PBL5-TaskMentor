package repository;

import model.GroupMeeting;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class GroupMeetingRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public GroupMeeting create(GroupMeeting groupMeeting) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(groupMeeting);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return groupMeeting;
    }
}

