package lucicd.travelbudget.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.exceptions.AppException;

public class GetDataHandler implements IHandler {
    protected void getData(ServletRequest req, ServletResponse res) 
        throws AppException {}
    
    @Override
    public void handleIt(ServletRequest req, ServletResponse res)
        throws AppException {
        getData(req, res);
    }
}
