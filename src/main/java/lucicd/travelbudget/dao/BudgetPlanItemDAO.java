package lucicd.travelbudget.dao;

import lucicd.travelbudget.beans.BudgetPlanItem;
import lucicd.travelbudget.exceptions.AppException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

public class BudgetPlanItemDAO {

    SessionFactory factory = null;
    Session session = null;

    private static BudgetPlanItemDAO singleInstance = null;

    private BudgetPlanItemDAO() {
        factory = HibernateUtils.getSessionFactory();
    }

    public static BudgetPlanItemDAO getInstance() {
        if (singleInstance == null) {
            singleInstance = new BudgetPlanItemDAO();
        }
        return singleInstance;
    }

    public List<Object[]> getBudgetPlanItems(Integer budgetPlanId)
            throws AppException 
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = new StringBuilder()
                .append("SELECT")
                .append(" budget_plan_items.id id")
                .append(", budget_plan_items.budget_plan_id budget_plan_id")
                .append(", budget_plan_items.item_description item_description")
                .append(", budget_plan_items.url url")
                .append(", budget_plan_items.start_date start_date")
                .append(", budget_plan_items.completion_date completion_date")
                .append(", budget_plan_items.cost_in_currency cost_in_currency")
                .append(", budget_plan_items.status status")
                .append(", budget_plan_items.comments comments")
                .append(", budget_plan_items.exchange_rate exchange_rate")
                .append(", budget_plan_items.cost_in_currency * budget_plan_items.exchange_rate cost")
                .append(", budget_plan_items.currency_id currency_id")
                .append(", currencies.name currency")
                .append(", budget_plan_items.category_id category_id")
                .append(", categories.description category")
                .append(" FROM budget_plan_items")
                .append(" JOIN currencies ON budget_plan_items.currency_id = currencies.id")
                .append(" JOIN categories ON budget_plan_items.category_id = categories.id")
                .append(" WHERE budget_plan_items.budget_plan_id = :budgetPlanId")
                .append(" ORDER BY budget_plan_items.start_date, budget_plan_items.id")
                .toString();
            NativeQuery query = session.createSQLQuery(sql);
            query.setParameter("budgetPlanId", budgetPlanId);
            List<Object[]> budgetPlanItems = query.list();
            session.getTransaction().commit();
            return budgetPlanItems;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get budget plans items. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public BudgetPlanItem getBudgetPlanItem(int id) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.BudgetPlanItem where id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            BudgetPlanItem rec = (BudgetPlanItem)query.getSingleResult();
            session.getTransaction().commit();
            return rec;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get budget plan item. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void addBudgetPlanItem(BudgetPlanItem rec) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer) session.save(rec);
            session.getTransaction().commit();
            rec.setId(id);
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            throw new AppException("Failed to add budget plan item." 
                    + " " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void updateBudgetPlanItem(BudgetPlanItem rec) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.update(rec);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to update budget plan item. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void deleteBudgetPlanItem(BudgetPlanItem rec) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.delete(rec);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to delete budget plan item. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public Long getCount() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "select count(t.id) from lucicd.travelbudget.beans.BudgetPlanItem t";
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
            sql = "TRUNCATE TABLE budget_plan_items";
            NativeQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to truncate budget_plan_items table. " 
                    + ex.getMessage());
        } finally {
            session.close();
        }
    }
}