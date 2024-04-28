package repository;

import model.Team;
import model.TeamMember;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import org.hibernate.query.Query;


import java.util.List;

public class TeamMemberRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public TeamMember save(TeamMember teamMember) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(teamMember);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return teamMember;
    }

    public List<TeamMember> getTeamMembersByTeam(int teamId) {
        try (Session session = sessionFactory.openSession()) {
            Query<TeamMember> query = session.createQuery("FROM TeamMember WHERE team.id = :teamId", TeamMember.class);
            query.setParameter("teamId", teamId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TeamMember> getTeamMemberByStudentCode(String studentCode) {
        try (Session session = sessionFactory.openSession()) {
            Query<TeamMember> query = session.createQuery("FROM TeamMember WHERE student.code = :studentCode AND deleteTime IS NULL AND team.deleteTime IS NULL", TeamMember.class);
            query.setParameter("studentCode", studentCode);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
