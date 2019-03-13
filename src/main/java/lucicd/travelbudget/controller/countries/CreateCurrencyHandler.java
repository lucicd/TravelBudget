package lucicd.travelbudget.controller.countries;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.controller.Handler;

public class CreateCurrencyHandler implements Handler
{
    @Override
    public void handleIt(ServletRequest req, ServletResponse res)
            throws ServletException, IOException
    {
        RequestDispatcher dispatcher = req.getRequestDispatcher(
                    "/WEB-INF/currencies/form.jsp");
        dispatcher.forward(req, res);
    }
    
}
