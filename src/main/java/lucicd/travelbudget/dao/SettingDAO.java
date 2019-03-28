package lucicd.travelbudget.dao;

import lucicd.travelbudget.beans.Setting;
import lucicd.travelbudget.exceptions.AppException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

public class SettingDAO {
    SessionFactory factory = null;
    Session session = null;
    
    private static SettingDAO singleInstance = null;
    
    private SettingDAO()
    {
        factory = HibernateUtils.getSessionFactory();
    }
    
    public static SettingDAO getInstance()
    {
        if (singleInstance == null)
        {
            singleInstance = new SettingDAO();
        }
        return singleInstance;
    }
    
    public List<Setting> getSettings() throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Setting";
            List<Setting> settings;
            settings = (List<Setting>)session.createQuery(sql).getResultList();
            session.getTransaction().commit();
            return settings;
        } catch(HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get settings. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Setting getSetting(int id) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Setting where id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            Setting setting = (Setting)query.getSingleResult();
            session.getTransaction().commit();
            return setting;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get setting. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void addSetting(Setting setting) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer)session.save(setting);
            session.getTransaction().commit();
            setting.setId(id);
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to add setting. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void updateSetting(Setting setting) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.update(setting);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to update setting. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void deleteSetting(Setting setting) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.delete(setting);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to delete setting. " + ex.getMessage());
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
            sql = "select count(t.id) from lucicd.travelbudget.beans.Setting t";
            Long count = (Long)session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
            return count; 
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to count settings. " + ex.getMessage());
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
            sql = "TRUNCATE TABLE settings";
            NativeQuery ps = session.createSQLQuery(sql);
            ps.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
             throw new AppException("Failed to truncate settings table. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
}
