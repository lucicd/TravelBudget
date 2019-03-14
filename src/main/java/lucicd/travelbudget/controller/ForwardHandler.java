package lucicd.travelbudget.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.exceptions.AppException;

public class ForwardHandler implements IHandler {
    private final String url;
    
    public ForwardHandler(String url)
    {
        this.url = url;
    }
    
    protected void init(ServletRequest req, ServletResponse res) 
        throws AppException {}
    
    @Override
    public void handleIt(ServletRequest req, ServletResponse res)
            throws AppException {
        try {
            init(req, res);
            RequestDispatcher dispatcher = req.getRequestDispatcher(url);
            dispatcher.forward(req, res);
        } catch (ServletException | IOException ex) {
            throw new AppException(ex.getMessage());
        }
    }
}
