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
import lucicd.travelbudget.beans.Category;
import lucicd.travelbudget.dao.CategoryDAO;
import lucicd.travelbudget.forms.CategoryForm;
import lucicd.travelbudget.validators.CategoryValidator;

public class CategoryController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private void getOne(HttpServletRequest req) throws AppException
    {
        try {
            String id = req.getParameter("id");
            Category rec = CategoryDAO.getInstance()
                    .getCategory(Integer.parseInt(id));
            CategoryForm form = new CategoryForm();
            form.setId(rec.getId().toString());
            form.setDescription(rec.getDescription());
            req.setAttribute("formData", rec);
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        List<Category> data = CategoryDAO.getInstance().getCategories();
        req.setAttribute("listData", data);
    }

    private CategoryController() {
        
        mapAction("list", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException
            {
                getMany(req);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/categories/index.jsp");
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
                CategoryForm form = new CategoryForm();
                req.setAttribute("formData", form);
                RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/categories/form.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/categories/form.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/categories/details.jsp");
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
                        req.getRequestDispatcher("/WEB-INF/categories/details.jsp");
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
                CategoryForm form = new CategoryForm();
                form.setId(req.getParameter("id"));
                form.setDescription(req.getParameter("description"));
                CategoryValidator validator = new CategoryValidator();
                Map<String, String> errors = validator.validate(form);
                if (errors.isEmpty()) {
                    CategoryDAO dao = CategoryDAO.getInstance();
                    Category rec = new Category();
                    rec.setDescription(form.getDescription());
                    String id = form.getId();
                    if (id == null || id.trim().isEmpty()) {
                        dao.addCategory(rec);
                    } else {
                        rec.setId(Integer.parseInt(id));
                        dao.updateCategory(rec);
                    }
                    getMany(req);
                    try {
                        res.sendRedirect("./categories");
                    } catch (IOException ex) {
                        throw new AppException(ex.getMessage());
                    }
                } else {
                    req.setAttribute("formData", form);
                    req.setAttribute("formErrors", errors);
                    RequestDispatcher rd = 
                            req.getRequestDispatcher("/WEB-INF/categories/form.jsp");
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
                Category rec = CategoryDAO.getInstance()
                        .getCategory(Integer.parseInt(id));
                CategoryDAO.getInstance().deleteCategory(rec);
                getMany(req);
                try {
                    res.sendRedirect("./categories");
                } catch (IOException ex) {
                    throw new AppException(ex.getMessage());
                }
            }
        });
    }

    public static IController getInstance() {
        if (controller == null) {
            controller = new CategoryController();
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