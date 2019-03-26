package lucicd.travelbudget.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.exceptions.AppException;

public interface IHandler {
    public void handleIt(HttpServletRequest req, HttpServletResponse res)
            throws AppException;
}