package lucicd.travelbudget.dao;

import lucicd.travelbudget.beans.BudgetPlan;
import lucicd.travelbudget.exceptions.AppException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

public class BudgetPlanDAO {

    SessionFactory factory = null;
    Session session = null;

    private static BudgetPlanDAO singleInstance = null;

    private BudgetPlanDAO() {
        factory = HibernateUtils.getSessionFactory();
    }

    public static BudgetPlanDAO getInstance() {
        if (singleInstance == null) {
            singleInstance = new BudgetPlanDAO();
        }
        return singleInstance;
    }

    public List<Object[]> getBudgetPlans() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = new StringBuilder()
                    .append("SELECT")
                    .append(" budget_plans.id id,")
                    .append(" budget_plans.travel_date travel_date,")
                    .append(" budget_plans.travel_destination travel_destination,")
                    .append(" budget_plans.available_budget available_budget,")
                    .append(" currencies.name currency")
                    .append(" FROM budget_plans")
                    .append(" JOIN currencies ON budget_plans.currency_id = currencies.id")
                    .append(" ORDER BY budget_plans.travel_date DESC")
                    .toString();
            NativeQuery query = session.createSQLQuery(sql);
            List<Object[]> budgetPlans = query.list();
            session.getTransaction().commit();
            return budgetPlans;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get budget plans. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public BudgetPlan getBudgetPlan(int id) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.BudgetPlan where id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            BudgetPlan setting = (BudgetPlan)query.getSingleResult();
            session.getTransaction().commit();
            return setting;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get budget plan. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void addBudgetPlan(BudgetPlan setting) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer) session.save(setting);
            session.getTransaction().commit();
            setting.setId(id);
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to add budget plan. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void updateBudgetPlan(BudgetPlan setting) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.update(setting);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to update budget plan. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void deleteBudgetPlan(BudgetPlan setting) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.delete(setting);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to delete budget plan. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public Long getCount() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "select count(t.id) from lucicd.travelbudget.beans.BudgetPlan t";
            Long count = (Long) session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
            return count;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to count budget plans. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void truncate() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "TRUNCATE TABLE budget_plan";
            NativeQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to truncate budget_plan table. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
}