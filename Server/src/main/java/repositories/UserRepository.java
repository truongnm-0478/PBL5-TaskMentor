package repositories;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import org.hibernate.query.Query;
import java.util.List;

public class UserRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();

        try {
            return session.createQuery("FROM User", User.class).list();
        } finally {
            session.close();
        }
    }
    public User getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM User WHERE username = :username");
            query.setParameter("username", username);
            return (User) query.uniqueResult();
        }
    }

    public User getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM User WHERE email = :email");
            query.setParameter("email", email);
            return (User) query.uniqueResult();
        }
    }

    public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
