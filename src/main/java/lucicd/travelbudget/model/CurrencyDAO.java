package lucicd.travelbudget.model;

import lucicd.travelbudget.exceptions.ModelException;
import java.util.List;
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
    
    public List<Currency> getCurrencies() throws ModelException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.model.Currency";
            List<Currency> currencies;
            currencies = (List<Currency>)session.createQuery(sql).getResultList();
            session.getTransaction().commit();
            return currencies;
        } catch(HibernateException ex) {
            session.getTransaction().rollback();
            throw new ModelException("Failed to get currencies. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Currency getCurrency(int id) throws ModelException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "from lucicd.travelbudget.model.Currency where id=" + Integer.toString(id);
            Currency currency;
            currency = (Currency)session.createQuery(sql).getSingleResult();
            session.getTransaction().commit();
            return currency;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new ModelException("Failed to get currency. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Integer addCurrency(Currency currency) throws ModelException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer)session.save(currency);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new ModelException("Failed to add currency. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Long getCount() throws ModelException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql;
            sql = "select count(t.id) from lucicd.travelbudget.model.Currency t";
            Long count = (Long)session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
            return count; 
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new ModelException("Failed to count currencies. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void truncate() throws ModelException
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
             throw new ModelException("Failed to truncate currencies table. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
}
