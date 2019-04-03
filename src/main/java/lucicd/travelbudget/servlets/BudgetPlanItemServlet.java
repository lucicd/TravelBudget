package lucicd.travelbudget.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.controller.BudgetPlanItemController;
import lucicd.travelbudget.exceptions.AppException;

@WebServlet(name = "Budget Plan Items", urlPatterns = {"/budget-plan-items"})
public class BudgetPlanItemServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) 
            throws ServletException, IOException
    {
        try {
            String action = req.getParameter("action");
            BudgetPlanItemController.getInstance().handleRequest(action, req, res);
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
