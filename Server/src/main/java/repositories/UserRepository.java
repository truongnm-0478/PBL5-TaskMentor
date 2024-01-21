package repositories;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

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
}
