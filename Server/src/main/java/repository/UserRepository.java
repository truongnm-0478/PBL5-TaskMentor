package repositories;

import dto.UserAdminDTO;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import org.hibernate.query.Query;

import java.util.List;

public class UserRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<UserAdminDTO> getAllUsers(int pageNumber, int pageSize) {
        try (Session session = sessionFactory.openSession()) {
            String jpql = "SELECT new dto.UserAdminDTO(u.id, u.email, u.username, u.role, u.name, u.phone, u.deleteTime, u.deleteBy, u.insertTime, u.insertBy, u.updateTime, u.updateBy) " +
                    "FROM User u";
            Query query = session.createQuery(jpql);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        }
    }

    public int getTotalUsers() {
        try (Session session = sessionFactory.openSession()) {
            String countQuery = "SELECT COUNT(u.id) FROM User u";
            Query<Long> query = session.createQuery(countQuery, Long.class);
            return Math.toIntExact(query.uniqueResult());
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

    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
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
