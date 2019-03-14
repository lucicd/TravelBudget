package lucicd.travelbudget.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.exceptions.AppException;

public interface IController {
    public void handleRequest(String action, 
            ServletRequest req, ServletResponse res)
            throws AppException;
}
