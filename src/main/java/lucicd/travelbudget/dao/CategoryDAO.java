package lucicd.travelbudget.dao;

import lucicd.travelbudget.beans.Category;
import lucicd.travelbudget.exceptions.AppException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

public class CategoryDAO {
    SessionFactory factory = null;
    Session session = null;
    
    private static CategoryDAO singleInstance = null;
    
    private CategoryDAO()
    {
        factory = HibernateUtils.getSessionFactory();
    }
    
    public static CategoryDAO getInstance()
    {
        if (singleInstance == null)
        {
            singleInstance = new CategoryDAO();
        }
        return singleInstance;
    }
    
    public List<Category> getCategories() throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Category";
            List<Category> categories;
            categories = (List<Category>)session.createQuery(sql).getResultList();
            session.getTransaction().commit();
            return categories;
        } catch(HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get categories. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public Category getCategory(int id) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            String sql = "from lucicd.travelbudget.beans.Category where id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            Category category = (Category)query.getSingleResult();
            session.getTransaction().commit();
            return category;
        } catch (HibernateException | NoResultException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to get category. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void addCategory(Category category) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            Integer id = (Integer)session.save(category);
            session.getTransaction().commit();
            category.setId(id);
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to add category. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void updateCategory(Category category) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.update(category);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to update category. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    public void deleteCategory(Category category) throws AppException
    {
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.delete(category);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to delete category. " + ex.getMessage());
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
            sql = "select count(t.id) from lucicd.travelbudget.beans.Category t";
            Long count = (Long)session.createQuery(sql).uniqueResult();
            session.getTransaction().commit();
            return count; 
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
            throw new AppException("Failed to count categories. " + ex.getMessage());
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
            sql = "TRUNCATE TABLE categories";
            NativeQuery ps = session.createSQLQuery(sql);
            ps.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            session.getTransaction().rollback();
             throw new AppException("Failed to truncate categories table. " + ex.getMessage());
        } finally {
            session.close();
        }
    }
}
