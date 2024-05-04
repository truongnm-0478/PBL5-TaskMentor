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
                    "FROM Notification WHERE classRoom.code = :classCode AND deleteTime IS NULL ORDER BY insertTime DESC", Notification.class
            );
            query.setParameter("classCode", classCode);
            notifications = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public Notification getById(int notificationId) {
        Notification notification = null;
        try (Session session = sessionFactory.openSession()) {
            notification = session.get(Notification.class, notificationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notification;
    }

    public void update(Notification notification) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(notification);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getNotificationsByUserId(Integer userId) {
        List<Notification> notifications = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Notification> query = session.createNativeQuery(
                    "SELECT * FROM notifications WHERE ? = ANY(user_ids) AND delete_time IS NULL ORDER BY insert_time DESC", Notification.class
            );
            query.setParameter(1, userId);
            notifications = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

}
