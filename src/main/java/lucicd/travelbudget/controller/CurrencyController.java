package lucicd.travelbudget.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.dao.CurrencyDAO;
import lucicd.travelbudget.forms.CurrencyForm;
import lucicd.travelbudget.validators.CurrencyValidator;

public class CurrencyController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;

    private CurrencyController() {
        mapAction("index", new IHandler() 
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res)
                    throws AppException 
            {
                RequestDispatcher rd;
                rd = req.getRequestDispatcher("/WEB-INF/currencies/index.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }            
        });
        
        mapAction("form", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res) 
                    throws AppException 
            {
                RequestDispatcher rd;
                rd = req.getRequestDispatcher("/WEB-INF/currencies/form.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
        
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res) 
                    throws AppException
            {
                controller.handleRequest("#get-many", req, res);
                controller.handleRequest("index", req, res);
            }
        });
        
        mapAction("create", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res)
                    throws AppException 
            {
                CurrencyForm form = new CurrencyForm();
                req.setAttribute("formData", form);
                controller.handleRequest("form", req, res);
            }
        });
        
        mapAction("edit", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res) 
                    throws AppException 
            {
                controller.handleRequest("#get-one", req, res);
                controller.handleRequest("form", req, res);
            }
        });

        mapAction("save", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res)
                    throws AppException 
            {
                CurrencyForm form = new CurrencyForm();
                form.setId(req.getParameter("id"));
                form.setName(req.getParameter("name"));
                CurrencyValidator validator = new CurrencyValidator();
                Map<String, String> errors = validator.validate(form);
                if (errors.isEmpty()) {
                    CurrencyDAO dao = CurrencyDAO.getInstance();
                    Currency rec = new Currency();
                    rec.setName(form.getName());
                    String id = form.getId();
                    if (id == null || id.trim().isEmpty()) {
                        dao.addCurrency(rec);
                    } else {
                        rec.setId(Integer.parseInt(id));
                        dao.updateCurrency(rec);
                    }
                    controller.handleRequest("list", req, res);
                } else {
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);                 
                    controller.handleRequest("form", req, res);
                }
            }
            
        });
        
        mapAction("#get-many", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                List<Currency> data = CurrencyDAO.getInstance().getCurrencies();
                req.setAttribute("listData", data);
            }
        });

        mapAction("#get-one", new IHandler() 
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                String id = req.getParameter("id");
                Currency rec = CurrencyDAO.getInstance()
                        .getCurrency(Integer.parseInt(id));
                CurrencyForm form = new CurrencyForm();
                form.setId(rec.getId().toString());
                form.setName(rec.getName());
                req.setAttribute("formData", rec);
            }
        });
        
        mapAction("delete", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                String id = req.getParameter("id");
                Currency rec = CurrencyDAO.getInstance()
                        .getCurrency(Integer.parseInt(id));
                CurrencyDAO.getInstance().deleteCurrency(rec);
                controller.handleRequest("list", req, res);
            }
        });
        
        mapAction("no-handler", new IHandler()
        {
            @Override
            public void handleIt(ServletRequest req, ServletResponse res) 
                    throws AppException 
            {
                throw new AppException("No handler for given action.");
            }
            
        });
    }

    public static IController getInstance() {
        if (controller == null) {
            controller = new CurrencyController();
        }
        return controller;
    }

    @Override
    public void handleRequest(String action,
            ServletRequest req, ServletResponse res)
            throws AppException
    {
        IHandler actionHandler
                = handlerMap.get(action == null ? "list" : action);
        if (actionHandler == null) {
            actionHandler = handlerMap.get("no-handler");
        }
        actionHandler.handleIt(req, res);
    }

    private void mapAction(String action, IHandler handler) {
        handlerMap.put(action, handler);
    }
}