package lucicd.travelbudget.dao;

import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.exceptions.AppException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

public class CurrencyDAO {
    SessionFactory factory = null;
    Session session = null;
    
    private static CurrencyDAO singleInstance = null;
    
    private CurrencyDAO()
    {
        factory = HibernateUtils.getSessionFactory();
    }
    
    public static CurrencyDAO getInstance()
    {
        if (singleInstance == null)
        {
            singleInstance = new CurrencyDAO();
        }
        return singleInstance;
    }
    
    public List<Currency> getCurrencies() throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Currency";
            List<Currency> currencies;
            currencies = (List<Currency>)session.createQuery(sql).getResultList();
            session.getTransaction().commit();
            return currencies;
        } catch(HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get currencies. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Currency getCurrency(Integer id) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Currency where id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            Currency currency = (Currency)query.getSingleResult();
            session.getTransaction().commit();
            return currency;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get currency. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Currency getCurrencyByName(String name) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Currency where name = :name";
            Query query = session.createQuery(sql);
            query.setParameter("name", name);
            Currency currency = (Currency)query.getSingleResult();
            session.getTransaction().commit();
            return currency;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get currency by name. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void addCurrency(Currency currency) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer)session.save(currency);
            session.getTransaction().commit();
            currency.setId(id);
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to add currency. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void updateCurrency(Currency currency) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.update(currency);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to update currency. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void deleteCurrency(Currency currency) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.delete(currency);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to delete currency. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Long getCount() throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "select count(t.id) from lucicd.travelbudget.beans.Currency t";
            Long count = (Long)session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
            return count; 
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to count currencies. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void truncate() throws AppException
    {
         try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "TRUNCATE TABLE currencies";
            NativeQuery ps = session.createSQLQuery(sql);
            ps.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
             throw new AppException("Failed to truncate currencies table. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
}
