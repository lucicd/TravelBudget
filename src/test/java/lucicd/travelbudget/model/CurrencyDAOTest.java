package lucicd.travelbudget.model;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

public class CurrencyDAOTest {
    
    public void setUp() {
//        CurrencyDAO dao = CurrencyDAO.getInstance();
//        try {
//            dao.truncate();
//        } catch (ModelException ex) {
//            System.err.println("Unexpected exception. " + ex.getMessage());
//        }
    }

    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CurrencyDAO result = CurrencyDAO.getInstance();
        assertThat(result).isNotNull();
    }
}