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
import lucicd.travelbudget.beans.Setting;
import lucicd.travelbudget.dao.SettingDAO;
import lucicd.travelbudget.forms.SettingForm;
import lucicd.travelbudget.validators.SettingValidator;

public class SettingController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private void getOne(HttpServletRequest req) throws AppException
    {
        try {
            String id = req.getParameter("id");
            Setting rec = SettingDAO.getInstance()
                    .getSetting(Integer.parseInt(id));
            SettingForm form = new SettingForm();
            form.setId(rec.getId().toString());
            form.setName(rec.getName());
            form.setTextValue(rec.getTextValue());
            
            BigDecimal numberValue = rec.getNumberValue();
            if (numberValue != null) {
                form.setNumberValue(numberValue.toString());
            }
            
            req.setAttribute("formData", rec);
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        List<Setting> data = SettingDAO.getInstance().getSettings();
        req.setAttribute("listData", data);
    }

    private SettingController() {
        
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException
            {
                getMany(req);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/settings/index.jsp");
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
                SettingForm form = new SettingForm();
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/settings/form.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/settings/form.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/settings/details.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/settings/details.jsp");
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
                SettingForm form = new SettingForm();
                form.setId(req.getParameter("id"));
                form.setName(req.getParameter("name"));
                form.setTextValue(req.getParameter("textValue"));
                form.setNumberValue(req.getParameter("numberValue"));
                SettingValidator validator = new SettingValidator();
                Map<String, String> errors = validator.validate(form);
                if (errors.isEmpty()) {
                    SettingDAO dao = SettingDAO.getInstance();
                    Setting rec = new Setting();
                    rec.setName(form.getName());
                    rec.setTextValue(form.getTextValue());
                    
                    String numberValue = form.getNumberValue();
                    if (numberValue != null && numberValue.trim().length() > 0) {
                        rec.setNumberValue(new BigDecimal(form.getNumberValue()));
                    }
                    
                    String id = form.getId();
                    if (id == null || id.trim().isEmpty()) {
                        dao.addSetting(rec);
                    } else {
                        rec.setId(Integer.parseInt(id));
                        dao.updateSetting(rec);
                    }
                    getMany(req);
                    try {
                        res.sendRedirect("./settings");
                    } catch (IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                } else {
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);
                    RequestDispatcher rd = 
                            req.getRequestDispatcher("/WEB-INF/settings/form.jsp");
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
                Setting rec = SettingDAO.getInstance()
                        .getSetting(Integer.parseInt(id));
                SettingDAO.getInstance().deleteSetting(rec);
                getMany(req);
                try {
                    res.sendRedirect("./settings");
                } catch (IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
    }

    public static IController getInstance() {
        if (controller == null) {
            controller = new SettingController();
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