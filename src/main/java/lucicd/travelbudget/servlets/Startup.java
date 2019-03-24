package lucicd.travelbudget.servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.model.Currency;
import lucicd.travelbudget.model.CurrencyDAO;

@WebListener
public class Startup implements ServletContextListener  {
    private void bootstrapCurrencies()
    {
        CurrencyDAO dao = CurrencyDAO.getInstance();
         try {
            Long count = dao.getCount();
            if (count == 0)
            {
                dao.addCurrency(new Currency("AED"));
                dao.addCurrency(new Currency("USD"));
                dao.addCurrency(new Currency("EUR"));
                dao.addCurrency(new Currency("JPY"));
                dao.addCurrency(new Currency("PHP"));
                dao.addCurrency(new Currency("GBP"));
                dao.addCurrency(new Currency("HRK"));
            }
        } catch (AppException ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Web application started."); 
        bootstrapCurrencies();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Web application stopped.");
    }
}
