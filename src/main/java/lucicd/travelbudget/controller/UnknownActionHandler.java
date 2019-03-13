package lucicd.travelbudget.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class UnknownActionHandler implements Handler {
    @Override
    public void handleIt(ServletRequest req, 
            ServletResponse res)
    {
        res.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = res.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Unknown action</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Unknown action</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}