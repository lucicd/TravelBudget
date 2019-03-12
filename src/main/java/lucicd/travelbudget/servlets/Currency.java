package lucicd.travelbudget.servlets;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Currency", urlPatterns = {"/currencies"})
public class Currency extends GenericServlet {
    @Override
    public void service(ServletRequest req,
            ServletResponse res) 
            throws ServletException, IOException
    {
        RequestDispatcher dispatcher = req.getRequestDispatcher(
                    "/WEB-INF/currencies/list.jsp");
        dispatcher.forward(req, res);
    }
}
