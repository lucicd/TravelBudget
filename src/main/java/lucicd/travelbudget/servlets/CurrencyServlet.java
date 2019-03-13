package lucicd.travelbudget.servlets;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import lucicd.travelbudget.controller.countries.CurrencyController;

@WebServlet(name = "Currency", urlPatterns = {"/currencies"})
public class CurrencyServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req,
            ServletResponse res) 
            throws ServletException, IOException
    {
        String action = req.getParameter("action");
        CurrencyController.getInstance().handleRequest(action, req, res);
    }
}
