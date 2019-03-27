package lucicd.travelbudget.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.beans.ExchangeRate;
import lucicd.travelbudget.dao.ExchangeRateDAO;
import lucicd.travelbudget.forms.ExchangeRateForm;
import lucicd.travelbudget.validators.ExchangeRateValidator;

public class ExchangeRateController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private void getOne(HttpServletRequest req) throws AppException
    {
        String id;
        ExchangeRate rec;
        try {
            id = req.getParameter("id");
            rec = ExchangeRateDAO.getInstance().getExchangeRate(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
        ExchangeRateForm form = new ExchangeRateForm();
        form.setId(rec.getId().toString());

        BigDecimal currentExchangeRate = rec.getCurrentExchangeRate();
        if (currentExchangeRate != null) {
            form.setCurrentExchangeRate(currentExchangeRate.toString());
        }

        Integer currencyId = rec.getCurrencyId();
        if (currencyId != null) {
            form.setCurrencyId(currencyId.toString());
        }
            
        req.setAttribute("formData", rec);
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        List<Object[]> data = ExchangeRateDAO.getInstance().getExchangeRates();
        req.setAttribute("listData", data);
    }

    private ExchangeRateController() {
        
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException
            {
                getMany(req);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/exchange-rates/index.jsp");
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
                ExchangeRateForm form = new ExchangeRateForm();
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/exchange-rates/form.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/exchange-rates/form.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/exchange-rates/details.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/exchange-rates/details.jsp");
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
                ExchangeRateForm form = new ExchangeRateForm();
                form.setId(req.getParameter("id"));
                form.setCurrentExchangeRate(req.getParameter("currentExchangeRate"));
                form.setCurrencyId(req.getParameter("currencyId"));
                ExchangeRateValidator validator = new ExchangeRateValidator();
                Map<String, String> errors = validator.validate(form);
                if (errors.isEmpty()) {
                    ExchangeRateDAO dao = ExchangeRateDAO.getInstance();
                    ExchangeRate rec = new ExchangeRate();
                    
                    String currentExchangeRate = form.getCurrentExchangeRate();
                    if (currentExchangeRate != null && currentExchangeRate.trim().length() > 0) {
                        rec.setCurrentExchangeRate(new BigDecimal(form.getCurrentExchangeRate()));
                    }
                    
                    String currencyId = form.getCurrencyId();
                    if (currencyId != null && currencyId.trim().length() > 0) {
                        rec.setCurrencyId(Integer.parseInt(currencyId));
                    }
                    
                    String id = form.getId();
                    if (id == null || id.trim().isEmpty()) {
                        dao.addExchangeRate(rec);
                    } else {
                        rec.setId(Integer.parseInt(id));
                        dao.updateExchangeRate(rec);
                    }
                    getMany(req);
                    try {
                        res.sendRedirect("./exchange-rates");
                    } catch (IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                } else {
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);
                    RequestDispatcher rd = 
                            req.getRequestDispatcher("/WEB-INF/exchange-rates/form.jsp");
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
                ExchangeRate rec = ExchangeRateDAO.getInstance()
                        .getExchangeRate(Integer.parseInt(id));
                ExchangeRateDAO.getInstance().deleteExchangeRate(rec);
                getMany(req);
                try {
                    res.sendRedirect("./exchange-rates");
                } catch (IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
    }

    public static IController getInstance() {
        if (controller == null) {
            controller = new ExchangeRateController();
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