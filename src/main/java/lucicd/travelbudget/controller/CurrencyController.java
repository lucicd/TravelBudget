package lucicd.travelbudget.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.model.Currency;
import lucicd.travelbudget.model.CurrencyDAO;

public class CurrencyController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;

    private CurrencyController() {
        mapAction("list", new ForwardHandler("/WEB-INF/currencies/list.jsp") {
            @Override
            protected void init(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                controller.handleRequest("getMany", req, res);
            }
        });

        mapAction("create", new ForwardHandler("/WEB-INF/currencies/form.jsp"));

        mapAction("edit", new ForwardHandler("/WEB-INF/currencies/form.jsp") {
            @Override
            protected void init(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                controller.handleRequest("getOne", req, res);
            }
        });

        mapAction("getMany", new GetDataHandler() {
            @Override
            protected void getData(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                List<Currency> data = CurrencyDAO.getInstance().getCurrencies();
                req.setAttribute("data", data);
            }
        });

        mapAction("getOne", new GetDataHandler() {
            @Override
            protected void getData(ServletRequest req, ServletResponse res)
                    throws AppException
            {
                String id = req.getParameter("id");
                Currency rec = CurrencyDAO.getInstance()
                        .getCurrency(Integer.parseInt(id));
                req.setAttribute("rec", rec);
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
            throws AppException {
        IHandler actionHandler
                = handlerMap.get(action == null ? "list" : action);
        if (actionHandler == null) {
            throw new AppException("No handler for action '" + action + "'.");
        }
        actionHandler.handleIt(req, res);
    }

    private void mapAction(String action, IHandler handler) {
        handlerMap.put(action, handler);
    }
}