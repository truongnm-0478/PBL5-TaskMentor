package repository;

import model.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class NotificationRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveNotification(Notification notification) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(notification);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getNotificationsByClassCode(String classCode) {
        List<Notification> notifications = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Notification> query = session.createQuery(
                    "FROM Notification WHERE classRoom.code = :classCode ORDER BY insertTime DESC", Notification.class
            );
            query.setParameter("classCode", classCode);
            notifications = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }


}
