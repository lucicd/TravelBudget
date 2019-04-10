package lucicd.travelbudget.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.beans.BudgetPlan;
import lucicd.travelbudget.beans.Setting;
import lucicd.travelbudget.dao.CurrencyDAO;
import lucicd.travelbudget.dao.BudgetPlanDAO;
import lucicd.travelbudget.dao.PaginatedList;
import lucicd.travelbudget.dao.SettingDAO;
import lucicd.travelbudget.forms.BudgetPlanForm;
import lucicd.travelbudget.validators.BudgetPlanValidator;

public class BudgetPlanController implements IController {

    private final int PAGE_SIZE = 5;
    
    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private BudgetPlan getOne(HttpServletRequest req) throws AppException
    {
        String id;
        BudgetPlan rec;
        try {
            id = req.getParameter("id");
            rec = BudgetPlanDAO.getInstance().getBudgetPlan(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
        return rec;
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        String pageNumberStr = req.getParameter("pageNumber");
        int pageNumber = 1;
        if (pageNumberStr != null && pageNumberStr.length() > 0)
        {
            try {
                pageNumber = Integer.parseInt(pageNumberStr);
            } catch (NumberFormatException ex) {
                throw new AppException("pageNumber is not a number. " 
                        + ex.getMessage());
            }
        }
        
        String sortOrder = req.getParameter("sortOrder");
        req.setAttribute("currentFilter", sortOrder);
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "date_desc";
        }
        
        String destinationSortOrder = 
                "destination".equals(sortOrder) 
                ? "destination_desc" 
                : "destination";
        req.setAttribute("destinationSortOrder", destinationSortOrder);
        
        String dateSortOrder = 
                "date".equals(sortOrder) 
                ? "date_desc" 
                : "date";
        req.setAttribute("dateSortOrder", dateSortOrder);
        
        String availableBudgetSortOrder = 
                "availableBudget".equals(sortOrder) 
                ? "availableBudget_desc" 
                : "availableBudget";
        req.setAttribute("availableBudgetSortOrder", availableBudgetSortOrder);
        
        String allocatedBudgetSortOrder = 
                "allocatedBudget".equals(sortOrder) 
                ? "allocatedBudget_desc" 
                : "allocatedBudget";
        req.setAttribute("allocatedBudgetSortOrder", allocatedBudgetSortOrder);
        
        String searchString = req.getParameter("searchString");
        String currentFilter = req.getParameter("currentFilter");
        if (searchString != null)
        {
            pageNumber = 1;
        } else {
            searchString = currentFilter;
        }
        req.setAttribute("currentFilter", searchString);
        
        PaginatedList data = BudgetPlanDAO.getInstance()
                .getBudgetPlans(PAGE_SIZE, pageNumber, searchString, sortOrder);
        req.setAttribute("listData", data);
    }
    
    private String getCurrencyName(String id) 
            throws AppException
    {
        return getCurrencyName(Integer.parseInt(id));
    }
    
    private String getCurrencyName(Integer id) 
            throws AppException
    {
        Currency rec = CurrencyDAO.getInstance().getCurrency(id);
        return rec.getName();
    }
    
    private Integer getReferenceCurrencyId() throws AppException
    {
        Setting rec = SettingDAO.getInstance()
                .getSettingByName("Reference Currency");
        String refCurrency;
        if (rec != null)
        {
            refCurrency = rec.getTextValue();
        } else {
            refCurrency = "EUR";
        }
        Currency currency = CurrencyDAO.getInstance().getCurrencyByName(refCurrency);
        return currency.getId();
    }
    
    private BudgetPlanController() {
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException
            {
                getMany(req);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plans/index.jsp");
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
                BudgetPlanForm form = new BudgetPlanForm();
                Integer currencyId = getReferenceCurrencyId();
                form.setCurrencyId(currencyId.toString());
                form.setCurrencyName(getCurrencyName(currencyId));
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plans/form.jsp");
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
                BudgetPlan rec = getOne(req);
                BudgetPlanForm form = new BudgetPlanForm(rec);
                form.setCurrencyName(getCurrencyName(rec.getCurrencyId()));
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plans/form.jsp");
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
                BudgetPlan rec = getOne(req);
                BudgetPlanForm form = new BudgetPlanForm(rec);
                form.setCurrencyName(getCurrencyName(rec.getCurrencyId()));
                Object allocatedBudget = BudgetPlanDAO.getInstance().getAllocatedBudget(rec.getId());
                form.setAllocatedBudget(allocatedBudget.toString());
                req.setAttribute("formData", form);
                req.setAttribute("action", "delete");
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plans/details.jsp");
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
                BudgetPlan rec = getOne(req);
                BudgetPlanForm form = new BudgetPlanForm(rec);
                form.setCurrencyName(getCurrencyName(rec.getCurrencyId()));
                Object allocatedBudget = BudgetPlanDAO.getInstance().getAllocatedBudget(rec.getId());
                if (allocatedBudget != null) {
                    form.setAllocatedBudget(allocatedBudget.toString());
                } else {
                    form.setAllocatedBudget("0");
                }
                req.setAttribute("formData", form);
                req.setAttribute("action", "details");
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plans/details.jsp");
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
                BudgetPlanForm form = new BudgetPlanForm();
                form.setId(req.getParameter("id"));
                form.setTravelDate(req.getParameter("travelDate"));
                form.setTravelDestination(req.getParameter("travelDestination").trim());
                form.setAvailableBudget(req.getParameter("availableBudget"));
                form.setComments(req.getParameter("comments").trim());
                form.setCurrencyId(req.getParameter("currencyId"));
                BudgetPlanValidator validator = new BudgetPlanValidator();
                Map<String, String> errors = validator.validate(form);
                if (errors.isEmpty()) {
                    BudgetPlan rec = new BudgetPlan();
                    BudgetPlanDAO dao = BudgetPlanDAO.getInstance();
                                        
                    String travelDate = form.getTravelDate();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    try {
                        rec.setTravelDate(df.parse(travelDate));
                    } catch (ParseException ex) {
                        throw new AppException(ex.getMessage());
                    }
                    
                    rec.setTravelDestination(form.getTravelDestination());
                    
                    String availableBudget = form.getAvailableBudget();
                    rec.setAvailableBudget(new BigDecimal(availableBudget));
                    
                    rec.setComments(form.getComments());
                    
                    String currencyId = form.getCurrencyId();
                    rec.setCurrencyId(Integer.parseInt(currencyId));
                    
                    String id = form.getId();
                    if (id == null || id.trim().isEmpty()) {
                        dao.addBudgetPlan(rec);
                    } else {
                        rec.setId(Integer.parseInt(id));
                        dao.updateBudgetPlan(rec);
                    }
                    getMany(req);
                    try {
                        res.sendRedirect("./budget-plans");
                    } catch (IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                } else {
                    form.setCurrencyName(
                            getCurrencyName(form.getCurrencyId()));
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);
                    RequestDispatcher rd = 
                            req.getRequestDispatcher("/WEB-INF/budget-plans/form.jsp");
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
                BudgetPlan rec = BudgetPlanDAO.getInstance()
                        .getBudgetPlan(Integer.parseInt(id));
                BudgetPlanDAO.getInstance().deleteBudgetPlan(rec);
                getMany(req);
                try {
                    res.sendRedirect("./budget-plans");
                } catch (IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
    }
    
    public static IController getInstance() {
        if (controller == null) {
            controller = new BudgetPlanController();
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