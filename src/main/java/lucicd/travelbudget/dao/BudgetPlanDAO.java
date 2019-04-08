package lucicd.travelbudget.dao;

import java.math.BigInteger;
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

    public PaginatedList getBudgetPlans(int pageSize, int pageNumber, 
            String search, String sortOrder) 
            throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            StringBuilder sqlBuilder = new StringBuilder()
                .append("SELECT SQL_CALC_FOUND_ROWS")
                .append(" budget_plans.id id")
                .append(", budget_plans.travel_date travel_date")
                .append(", budget_plans.travel_destination travel_destination")
                .append(", budget_plans.available_budget available_budget")
                .append(", currencies.name currency")
                .append(", COALESCE(SUM(budget_plan_items.cost_in_currency * budget_plan_items.exchange_rate), 0) allocated_budget")
                .append(", COUNT(budget_plan_items.id) items_count")
                .append(" FROM budget_plans")
                .append(" JOIN currencies ON budget_plans.currency_id = currencies.id")
                .append(" LEFT JOIN budget_plan_items ON budget_plans.id = budget_plan_items.budget_plan_id")
                .append(" WHERE budget_plans.travel_destination LIKE :search")
                .append(" GROUP BY")
                .append(" id")
                .append(", travel_date")
                .append(", travel_destination")
                .append(", available_budget")
                .append(", currency")
                .append(" ORDER BY ");
            switch (sortOrder)
            {
                case "destination":
                    sqlBuilder.append("travel_destination");
                    break;
                case "destination_desc":
                    sqlBuilder.append("travel_destination DESC");
                    break;
                case "date":
                    sqlBuilder.append("travel_date");
                    break;
                case "availableBudget":
                    sqlBuilder.append("available_budget");
                    break;
                case "availableBudget_desc":
                    sqlBuilder.append("available_budget DESC");
                    break;
                case "allocatedBudget":
                    sqlBuilder.append("allocated_budget");
                    break;
                case "allocatedBudget_desc":
                    sqlBuilder.append("allocated_budget DESC");
                    break;
                default:
                    sqlBuilder.append("travel_date DESC");
            }
            sqlBuilder.append(" LIMIT :limit OFFSET :offset");
            String sql = sqlBuilder.toString();
            NativeQuery query = session.createSQLQuery(sql);
            query.setParameter("limit", pageSize);
            query.setParameter("offset", (pageNumber - 1) * pageSize);
            if (search == null || search.trim().length() == 0) {
                query.setParameter("search", "%");
            } else {
                query.setParameter("search", "%" + search.trim() + "%");
            }
            PaginatedList paginatedList = new PaginatedList(pageSize);
            paginatedList.setList(query.list());
            query = session.createSQLQuery("SELECT FOUND_ROWS()");
            paginatedList.setNumRows((BigInteger)query.getSingleResult());
            paginatedList.setCurrentPage(pageNumber);
            session.getTransaction().commit();
            return paginatedList;
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