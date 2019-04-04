package lucicd.travelbudget.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.beans.Category;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.beans.BudgetPlanItem;
import lucicd.travelbudget.beans.Setting;
import lucicd.travelbudget.dao.CurrencyDAO;
import lucicd.travelbudget.dao.CategoryDAO;
import lucicd.travelbudget.dao.BudgetPlanItemDAO;
import lucicd.travelbudget.dao.SettingDAO;
import lucicd.travelbudget.forms.BudgetPlanItemForm;
import lucicd.travelbudget.validators.BudgetPlanItemValidator;

public class BudgetPlanItemController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private BudgetPlanItem getOne(HttpServletRequest req) throws AppException
    {
        String id;
        BudgetPlanItem rec;
        try {
            id = req.getParameter("id");
            rec = BudgetPlanItemDAO.getInstance()
                    .getBudgetPlanItem(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
        return rec;
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        String budgetPlanId;
        budgetPlanId = req.getParameter("budgetPlanId");
        try {
            Integer id = Integer.parseInt(budgetPlanId);
            List<Object[]> data = BudgetPlanItemDAO
                .getInstance().getBudgetPlanItems(id);
            req.setAttribute("listData", data);
        } catch (NumberFormatException ex) {
            throw new AppException("BudgetPlanID is not a number. " + ex.getMessage());
        }
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
    
    private String getCategoryDescription(String id) 
            throws AppException
    {
        return getCategoryDescription(Integer.parseInt(id));
    }
    
    private String getCategoryDescription(Integer id) 
            throws AppException
    {
        Category rec = CategoryDAO.getInstance().getCategory(id);
        return rec.getDescription();
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
    
    private BudgetPlanItemController() {
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException
            {
                getMany(req);
                RequestDispatcher rd = req.getRequestDispatcher(
                        "/WEB-INF/budget-plan-items/index.jsp?budgetPlanId="
                        + req.getParameter("budgetPlanId"));
                req.setAttribute("budgetPlanId", req.getParameter("budgetPlanId"));
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
                BudgetPlanItemForm form = new BudgetPlanItemForm();
                form.setBudgetPlanId(req.getParameter("budgetPlanId"));
                form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
                form.setCategories(CategoryDAO.getInstance().getCategories());
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plan-items/form.jsp");
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
                BudgetPlanItem rec = getOne(req);
                BudgetPlanItemForm form = new BudgetPlanItemForm(rec);
                form.setBudgetPlanId(rec.getBudgetPlanId().toString());
                form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
                form.setCategories(CategoryDAO.getInstance().getCategories());
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plan-items/form.jsp");
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
                BudgetPlanItem rec = getOne(req);
                BudgetPlanItemForm form = new BudgetPlanItemForm(rec);
                form.setCurrency(getCurrencyName(rec.getCurrencyId()));
                form.setCategory(getCategoryDescription(rec.getCategoryId()));
                req.setAttribute("formData", form);
                req.setAttribute("action", "delete");
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plan-items/details.jsp");
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
                BudgetPlanItem rec = getOne(req);
                BudgetPlanItemForm form = new BudgetPlanItemForm(rec);
                form.setCurrency(getCurrencyName(rec.getCurrencyId()));
                form.setCategory(getCategoryDescription(rec.getCategoryId()));
                req.setAttribute("formData", form);
                req.setAttribute("action", "details");
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/budget-plan-items/details.jsp");
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
                BudgetPlanItemForm form = new BudgetPlanItemForm();
                form.setId(req.getParameter("id"));
                form.setItemDescription(req.getParameter("itemDescription"));
                form.setUrl(req.getParameter("url").trim());
                form.setStartDate(req.getParameter("startDate"));
                form.setCompletionDate(req.getParameter("completionDate"));
                form.setCostInCurrency(req.getParameter("costInCurrency"));
                form.setStatus(req.getParameter("status"));
                form.setComments(req.getParameter("comments"));
                form.setBudgetPlanId(req.getParameter("budgetPlanId"));
                form.setCurrencyId(req.getParameter("currencyId"));
                form.setCategoryId(req.getParameter("categoryId"));
                form.setExchangeRate(req.getParameter("exchangeRate"));
                BudgetPlanItemValidator validator = new BudgetPlanItemValidator();
                Map<String, String> errors = validator.validate(form);
                if (errors.isEmpty()) {
                    BudgetPlanItem rec = new BudgetPlanItem();
                    BudgetPlanItemDAO dao = BudgetPlanItemDAO.getInstance();
                    
                    rec.setItemDescription(form.getItemDescription());
                    
                    rec.setUrl(form.getUrl());
                    
                    String startDate = form.getStartDate();
                    DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                    try {
                        rec.setStartDate(df.parse(startDate));
                    } catch (ParseException ex) {
                        throw new AppException(ex.getMessage());
                    }
                    
                    String completionDate = form.getCompletionDate();
                    try {
                        rec.setCompletionDate(df.parse(completionDate));
                    } catch (ParseException ex) {
                        throw new AppException(ex.getMessage());
                    }
                    
                    String costInCurrency = form.getCostInCurrency();
                    rec.setCostInCurrency(new BigDecimal(costInCurrency));
                    
                    rec.setStatus(form.getStatus());
                    
                    rec.setComments(form.getComments());
                    
                    String budgetPlanId = form.getBudgetPlanId();
                    rec.setBudgetPlanId(Integer.parseInt(budgetPlanId));
                    
                    String categoryId = form.getCategoryId();
                    rec.setCategoryId(Integer.parseInt(categoryId));
                    
                    String currencyId = form.getCurrencyId();
                    rec.setCurrencyId(Integer.parseInt(currencyId));
                    
                    String exchangeRate = form.getExchangeRate();
                    rec.setExchangeRate(new BigDecimal(exchangeRate));
                    
                    String id = form.getId();
                    if (id == null || id.trim().isEmpty()) {
                        dao.addBudgetPlanItem(rec);
                    } else {
                        rec.setId(Integer.parseInt(id));
                        dao.updateBudgetPlanItem(rec);
                    }
                    getMany(req);
                    try {
                        res.sendRedirect("./budget-plan-items?budgetPlanId="
                            + form.getBudgetPlanId());
                    } catch (IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                } else {
                    form.setBudgetPlanId(req.getParameter("budgetPlanId"));
                    form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
                    form.setCategories(CategoryDAO.getInstance().getCategories());
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);
                    RequestDispatcher rd = 
                            req.getRequestDispatcher("/WEB-INF/budget-plan-items/form.jsp");
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
                BudgetPlanItem rec = BudgetPlanItemDAO.getInstance()
                        .getBudgetPlanItem(Integer.parseInt(id));
                BudgetPlanItemDAO.getInstance().deleteBudgetPlanItem(rec);
                getMany(req);
                try {
                    res.sendRedirect("./budget-plan-items?budgetPlanId=" 
                            + rec.getBudgetPlanId().toString());
                } catch (IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
    }
    
    public static IController getInstance() {
        if (controller == null) {
            controller = new BudgetPlanItemController();
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