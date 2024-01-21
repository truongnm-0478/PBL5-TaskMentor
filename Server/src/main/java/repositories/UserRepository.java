package repositories;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import java.util.List;

public class UserRepository {
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }
}
