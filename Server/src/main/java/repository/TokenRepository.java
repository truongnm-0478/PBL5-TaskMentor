package repositories;

import model.Token;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;

public class TokenRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Token> getTokensByUserId(int userId) {
        Session session = sessionFactory.openSession();

        try {
            Query<Token> query = session.createQuery("FROM Token WHERE user_id = :userId", Token.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    public void saveToken(Token token) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(token);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("E: " + e);
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Token getTokenById(int tokenId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Token.class, tokenId);
        }
    }

    public void deleteToken(Token token) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(token);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Token getTokenByToken(String tokenValue) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Token WHERE token = :tokenValue");
            query.setParameter("tokenValue", tokenValue);
            return (Token) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRefreshTokenExists(String refreshToken) {
        try (Session session = sessionFactory.openSession()) {
            Token token = session.createQuery("FROM Token WHERE token = :refreshToken", Token.class)
                    .setParameter("refreshToken", refreshToken)
                    .uniqueResult();
            return token != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
