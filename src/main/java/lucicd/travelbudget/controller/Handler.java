package lucicd.travelbudget.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface Handler {
    public void handleIt(ServletRequest req, ServletResponse res)
            throws ServletException, IOException;
}