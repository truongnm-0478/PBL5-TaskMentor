package repository;

import model.Team;
import model.TeamMember;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class TeamRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Team save(Team team) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return team;
    }

    public Team getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Team.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Team> getTeamsByClassCode(String classCode) {
        try (Session session = sessionFactory.openSession()) {
            Query<Team> query = session.createQuery("FROM Team WHERE classRoom.code = :classCode AND deleteTime IS NULL", Team.class);
            query.setParameter("classCode", classCode);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Team update(Team team) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return team;
    }

}
