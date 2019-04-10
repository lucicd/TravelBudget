package lucicd.travelbudget.model;

import java.util.List;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.dao.CurrencyDAO;
import lucicd.travelbudget.exceptions.AppException;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

public class CurrencyDAOTest {
    
    private CurrencyDAO dao;
    
    private void prepare() {
        try {
            Currency rec = dao.getCurrencyByName("ZZZ");
            dao.deleteCurrency(rec);
            rec = dao.getCurrencyByName("BBB");
            dao.deleteCurrency(rec);
        } catch (Exception ignore) {}
    }
    
    @Before
    public void runBeforeTestMethod() {
        dao = CurrencyDAO.getInstance();
        prepare();
    }
    
    
    @Test
    public void testAddCurrency() {
        System.out.println("Test add currency");
        Currency rec;
        rec = new Currency();
        rec.setName("ZZZ");
        try {
            dao.addCurrency(rec);
        } catch (AppException ex) {
            fail("Unexpected exception while adding currency. " + ex.getMessage());
        }
    }
    
    @Test(expected=AppException.class)
    public void testAddDuplicateCurrency() throws Exception {
        System.out.println("Test add duplicate currency.");
        Currency rec  = new Currency();
        rec.setName("ZZZ");
        dao.addCurrency(rec);
        dao.addCurrency(rec);
    }
    
    @Test
    public void testGetCurrencyById() {
        System.out.println("Test get currency by id.");
        Currency rec = new Currency();
        rec.setName("ZZZ");
        try {
            dao.addCurrency(rec);
            assertThat(rec.getId()).isGreaterThan(0);
            rec = dao.getCurrency(rec.getId());
            assertThat(rec.getName()).isEqualTo("ZZZ");
        } catch (AppException ex) {
            fail("Unexpected exception while getting currency by id. " + ex.getMessage());
        }
    }
    
    @Test
    public void testUpdateCurrency() {
        System.out.println("Test update currency.");
        Currency rec = new Currency();
        rec.setName("ZZZ");
        try {
            dao.addCurrency(rec);
            rec.setName("BBB");
            dao.updateCurrency(rec);
            rec = dao.getCurrency(rec.getId());
            assertThat(rec.getName()).isEqualTo("BBB");
            dao.deleteCurrency(rec);
        } catch (AppException ex) {
            fail("Unexpected exception while testing update currency. " + ex.getMessage());
        }
    }
    
    @Test(expected=AppException.class)
    public void testDeleteCurrency() throws Exception {
        System.out.println("Test delete currency.");
        Currency rec = new Currency();
        rec.setName("ZZZ");
        dao.addCurrency(rec);
        dao.deleteCurrency(rec);
        rec = dao.getCurrency(rec.getId());
    }
    
    @Test
    public void testGetCurrencies() {
        System.out.println("Test get currencies.");
        try {
            Currency rec1 = new Currency();
            rec1.setName("ZZZ");
            dao.addCurrency(rec1);
            Currency rec2 = new Currency();
            rec2.setName("BBB");
            dao.addCurrency(rec2);
            List<Currency> list = dao.getCurrencies();
            assertThat(list).contains(rec1, rec2);
        } catch (AppException ex) {
            fail("Unexpected exception while getting currencies. " + ex.getMessage());
        }
    }
}