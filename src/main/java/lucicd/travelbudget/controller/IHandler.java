package lucicd.travelbudget.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.exceptions.AppException;

public interface IHandler {
    public void handleIt(ServletRequest req, ServletResponse res)
            throws AppException;
}