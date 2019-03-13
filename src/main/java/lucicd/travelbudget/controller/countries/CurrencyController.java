package lucicd.travelbudget.controller.countries;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lucicd.travelbudget.controller.Handler;
import lucicd.travelbudget.controller.UnknownActionHandler;

public class CurrencyController 
{
    private final Map<String, Handler> handlerMap = new HashMap();
    
    private static CurrencyController singleInstance = null;
    
    private CurrencyController()
    {
        mapAction("list", new ListCurrenciesHandler());
        mapAction("create", new CreateCurrencyHandler());
        mapAction("edit", new EditCurrencyHandler());
        mapAction("get", new GetCurrenciesHandler());
    }
    
    public static CurrencyController getInstance()
    {
        if (singleInstance == null)
        {
            singleInstance = new CurrencyController();
        }
        return singleInstance;
    }

    public void handleRequest(String action, 
            ServletRequest req, ServletResponse res)
            throws ServletException, IOException
    {
        Handler actionHandler = 
                handlerMap.get(action == null ? "list" : action);
        if (actionHandler == null)
        {
            actionHandler = new UnknownActionHandler();
        } 
        actionHandler.handleIt(req, res);
    }

    private void mapAction(String action, Handler handler)
    {
        handlerMap.put(action, handler);
    }
}
