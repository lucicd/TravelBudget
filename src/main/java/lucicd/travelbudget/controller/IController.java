package lucicd.travelbudget.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.exceptions.AppException;

public interface IController {
    public void handleRequest(String action, 
            HttpServletRequest req, HttpServletResponse res)
            throws AppException;
}
