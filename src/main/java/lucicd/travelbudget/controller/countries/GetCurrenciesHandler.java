package lucicd.travelbudget.controller.countries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.controller.Handler;
import lucicd.travelbudget.model.Currency;

public class GetCurrenciesHandler implements Handler {
    
    private List<Currency> getMany()
    {
        List<Currency> data = new ArrayList<>();
        data.add(new Currency(1, "AED"));
        data.add(new Currency(2, "USD"));
        data.add(new Currency(3, "EUR"));
        data.add(new Currency(4, "JPY"));
        data.add(new Currency(5, "PHP"));
        data.add(new Currency(6, "GBP"));
        data.add(new Currency(7, "HRK"));
        return data;
    }
    
    private Currency getOne(Integer id)
    {
        Currency rec = getMany().get(id);
        return rec;
    }

    @Override
    public void handleIt(ServletRequest req, ServletResponse res)
            throws ServletException, IOException 
    {
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("data", getMany());
        } else {
            req.setAttribute("rec", getOne(Integer.parseInt(id)));
        }
    }
}