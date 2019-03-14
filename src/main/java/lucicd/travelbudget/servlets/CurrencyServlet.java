package lucicd.travelbudget.servlets;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import lucicd.travelbudget.controller.CurrencyController;
import lucicd.travelbudget.exceptions.AppException;

@WebServlet(name = "Currency", urlPatterns = {"/currencies"})
public class CurrencyServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req,
            ServletResponse res) 
            throws ServletException, IOException
    {
        try {
            String action = req.getParameter("action");
            CurrencyController.getInstance().handleRequest(action, req, res);
        } catch (AppException ex) {
            req.setAttribute("error", ex.getMessage());
            String url = "/WEB-INF/shared/error.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(url);
            dispatcher.forward(req, res);
        }
    }
}
