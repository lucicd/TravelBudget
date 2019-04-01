package lucicd.travelbudget.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.controller.BudgetPlanController;
import lucicd.travelbudget.exceptions.AppException;

@WebServlet(name = "Budget Plans", urlPatterns = {"/budget-plans"})
public class BudgetPlanServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) 
            throws ServletException, IOException
    {
        try {
            String action = req.getParameter("action");
            BudgetPlanController.getInstance().handleRequest(action, req, res);
        } catch (AppException ex) {
            req.setAttribute("error", ex.getMessage());
            String url = "/WEB-INF/shared/error.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(url);
            dispatcher.forward(req, res);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }
}
