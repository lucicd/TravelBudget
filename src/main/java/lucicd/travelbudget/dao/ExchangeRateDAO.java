package lucicd.travelbudget.dao;

import lucicd.travelbudget.beans.ExchangeRate;
import lucicd.travelbudget.exceptions.AppException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

public class ExchangeRateDAO {

    SessionFactory factory = null;
    Session session = null;

    private static ExchangeRateDAO singleInstance = null;

    private ExchangeRateDAO() {
        factory = HibernateUtils.getSessionFactory();
    }

    public static ExchangeRateDAO getInstance() {
        if (singleInstance == null) {
            singleInstance = new ExchangeRateDAO();
        }
        return singleInstance;
    }

    public List<Object[]> getExchangeRates() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            //String sql = "from lucicd.travelbudget.beans.ExchangeRate";
            String sql = new StringBuilder()
                    .append("SELECT")
                    .append(" exchange_rates.id id,")
                    .append(" exchange_rates.current_exchange_rate current_exchange_rate,")
                    .append(" currencies.name currency")
                    .append(" FROM exchange_rates")
                    .append(" JOIN currencies ON exchange_rates.currency_id = currencies.id")
                    .append(" ORDER BY currencies.name")
                    .toString();
            NativeQuery query = session.createSQLQuery(sql);
            List<Object[]> exchangeRates = query.list();
            //exchangeRates = (List<ExchangeRate>)session.createQuery(sql).getResultList();
            session.getTransaction().commit();
            return exchangeRates;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get exchange rates. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public ExchangeRate getExchangeRate(int id) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.ExchangeRate where id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            ExchangeRate setting = (ExchangeRate)query.getSingleResult();
            session.getTransaction().commit();
            return setting;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get exchange rate. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public ExchangeRate getExchangeRateByCurrency(Integer currencyId) 
            throws AppException 
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.ExchangeRate"
                    + " where currency_id = :currencyId";
            Query query = session.createQuery(sql);
            query.setParameter("currencyId", currencyId);
            ExchangeRate setting = (ExchangeRate)query.getSingleResult();
            session.getTransaction().commit();
            return setting;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get exchange"
                    + " rate for currencyId = "
                    + currencyId.toString()
                    + ". " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void addExchangeRate(ExchangeRate setting) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer) session.save(setting);
            session.getTransaction().commit();
            setting.setId(id);
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to add exchange rate. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void updateExchangeRate(ExchangeRate setting) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.update(setting);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to update exchange rate. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void deleteExchangeRate(ExchangeRate setting) throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.delete(setting);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to delete exchange rate. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public Long getCount() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "select count(t.id) from lucicd.travelbudget.beans.ExchangeRate t";
            Long count = (Long) session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
            return count;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to count exchange rates. " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void truncate() throws AppException {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "TRUNCATE TABLE exchange_rates";
            NativeQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to truncate exchange_rates table. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
}
