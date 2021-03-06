package lucicd.travelbudget.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.dao.CurrencyDAO;
import lucicd.travelbudget.forms.CurrencyForm;
import lucicd.travelbudget.validators.CurrencyValidator;

public class CurrencyController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private void getOne(HttpServletRequest req) throws AppException
    {
        try {
            String id = req.getParameter("id");
            Currency rec = CurrencyDAO.getInstance()
                    .getCurrency(Integer.parseInt(id));
            CurrencyForm form = new CurrencyForm();
            form.setId(rec.getId().toString());
            form.setName(rec.getName());
            req.setAttribute("formData", rec);
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        List<Currency> data = CurrencyDAO.getInstance().getCurrencies();
        req.setAttribute("listData", data);
    }

    private CurrencyController() {
        
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException
            {
                getMany(req);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/currencies/index.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
        
        mapAction("create", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res)
                    throws AppException 
            {
                CurrencyForm form = new CurrencyForm();
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/currencies/form.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
        
        mapAction("edit", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException 
            {
                getOne(req);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/currencies/form.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
        
        mapAction("confirm-delete", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException 
            {
                getOne(req);
                req.setAttribute("action", "delete");
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/currencies/details.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
        
        mapAction("show-details", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException 
            {
                getOne(req);
                req.setAttribute("action", "details");
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/currencies/details.jsp");
                try {
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });

        mapAction("save", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res)
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
                    getMany(req);
                    try {
                        res.sendRedirect("./currencies");
                    } catch (IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                } else {
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);
                    RequestDispatcher rd = 
                            req.getRequestDispatcher("/WEB-INF/currencies/form.jsp");
                    try {
                        rd.forward(req, res);
                    } catch (ServletException | IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                }
            }
        });
        
        mapAction("delete", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res)
                    throws AppException
            {
                String id = req.getParameter("id");
                Currency rec = CurrencyDAO.getInstance()
                        .getCurrency(Integer.parseInt(id));
                CurrencyDAO.getInstance().deleteCurrency(rec);
                getMany(req);
                try {
                    res.sendRedirect("./currencies");
                } catch (IOException ex) {
                    throw new AppException(ex.getMessage());
                }
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
            HttpServletRequest req, HttpServletResponse res)
            throws AppException
    {
        IHandler actionHandler
                = handlerMap.get(action == null ? "list" : action);
        if (actionHandler == null) {
            throw new AppException("No handler for action " + action + ".");
        }
        actionHandler.handleIt(req, res);
    }

    private void mapAction(String action, IHandler handler) {
        handlerMap.put(action, handler);
    }
}