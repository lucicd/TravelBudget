package lucicd.travelbudget.controller.countries;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.controller.Handler;

public class ListCurrenciesHandler implements Handler {
    @Override
    public void handleIt(ServletRequest req, ServletResponse res)
            throws ServletException, IOException 
    {
        CurrencyController.getInstance().handleRequest("get", req, res);
        RequestDispatcher dispatcher = req.getRequestDispatcher(
                "/WEB-INF/currencies/list.jsp");
        dispatcher.forward(req, res);
    }

}
