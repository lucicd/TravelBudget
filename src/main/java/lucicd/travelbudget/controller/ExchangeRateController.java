package lucicd.travelbudget.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.beans.Currency;
import lucicd.travelbudget.exceptions.AppException;
import lucicd.travelbudget.beans.ExchangeRate;
import lucicd.travelbudget.beans.Setting;
import lucicd.travelbudget.dao.CurrencyDAO;
import lucicd.travelbudget.dao.ExchangeRateDAO;
import lucicd.travelbudget.dao.SettingDAO;
import lucicd.travelbudget.forms.ExchangeRateForm;
import lucicd.travelbudget.validators.ExchangeRateValidator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ExchangeRateController implements IController {

    private final Map<String, IHandler> handlerMap = new HashMap();

    private static IController controller = null;
    
    private ExchangeRate getOne(HttpServletRequest req) throws AppException
    {
        String id;
        ExchangeRate rec;
        try {
            id = req.getParameter("id");
            rec = ExchangeRateDAO.getInstance().getExchangeRate(Integer.parseInt(id));
        } catch (NumberFormatException ex) {
            throw new AppException("ID is not a number. " + ex.getMessage());
        }
        return rec;
    }
    
    private ExchangeRate getExchangeRateByCurrencyId(Integer currencyId) 
            throws AppException
    {
        ExchangeRate rec;
        try {
            rec = ExchangeRateDAO.getInstance()
                    .getExchangeRateByCurrency(currencyId);
        } catch (NumberFormatException ex) {
            throw new AppException("currencyId is not a number. " 
                    + ex.getMessage());
        }
        return rec;
    }
    
    private void getMany(HttpServletRequest req) throws AppException
    {
        List<Object[]> data = ExchangeRateDAO.getInstance().getExchangeRates();
        req.setAttribute("listData", data);
    }
    
    private String getCurrencyName(Integer id) 
            throws AppException
    {
        Currency rec = CurrencyDAO.getInstance().getCurrency(id);
        return rec.getName();
    }
    
    private String getReferenceCurrency() throws AppException
    {
        Setting rec = SettingDAO.getInstance()
                .getSettingByName("Reference Currency");
        if (rec != null)
        {
            return rec.getTextValue();
        } else {
            return "EUR";
        }
    }
    
    private String getExchangeRateFromAPI(String currencies) throws AppException
    {
        String result = "";
        String apiKey = "d1d2b7b8626f41c1b353";
        String proxyAddress = System.getenv("PROXY_ADDRESS");
        String proxyPort = System.getenv("PROXY_PORT");
        Proxy proxy = null;
        if (proxyAddress != null && proxyPort != null) {
            proxy = new Proxy(Proxy.Type.HTTP, 
                    new InetSocketAddress(proxyAddress, Integer.parseInt(proxyPort)));
        }
        try {
            URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q="
                + currencies + "&compact=ultra&apiKey="+ apiKey);
            URLConnection urlConnection;
            if (proxy == null) {
                urlConnection = url.openConnection();
            } else {
                urlConnection = url.openConnection(proxy);
            }
            urlConnection.setConnectTimeout(10 * 1000);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream()))) 
            {
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                {
                    result += inputLine;
                }
            } catch (UnknownHostException ex) {
                throw new AppException(ex.getMessage());    
            }
        } catch (MalformedURLException ex) {
            throw new AppException(ex.getMessage());
        } catch (IOException ex) {
            throw new AppException(ex.getMessage());
        }
        return result;
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
                form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
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
                ExchangeRate rec = getOne(req);
                ExchangeRateForm form = new ExchangeRateForm(rec);
                form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
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
                ExchangeRate rec = getOne(req);
                ExchangeRateForm form = new ExchangeRateForm(rec);
                form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
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
        
        mapAction("getRate", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException 
            {
                Integer currencyId = 
                        Integer.parseInt(req.getParameter("currencyId"));
                ExchangeRate rec = getExchangeRateByCurrencyId(currencyId);
                try (PrintWriter out = res.getWriter()) 
                {
                    res.setContentType( "application/json;charset=UTF-8");
                    JSONObject obj = new JSONObject();
                    obj.put("currentExchangeRate", 
                            rec.getCurrentExchangeRate().toString());
                    out.print(obj.toJSONString());
                } 
                catch (Exception ex) 
                {
                    throw new AppException("Can't get exchange rate as JSON. "
                        + ex.getMessage());
                }
            }
        });
        
        mapAction("show-details", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res) 
                    throws AppException 
            {
                ExchangeRate rec = getOne(req);
                ExchangeRateForm form = new ExchangeRateForm(rec);
                form.setCurrencyName(getCurrencyName(rec.getCurrencyId()));
                req.setAttribute("formData", form);
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
                    form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
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
        
        mapAction("rate-api", new IHandler()
        {
            @Override
            public void handleIt(HttpServletRequest req, HttpServletResponse res)
                    throws AppException
            {
                String currencyId = req.getParameter("currencyId");
                String currencyName;
                if (currencyId != null && currencyId.trim().length() > 0) {
                    currencyName = getCurrencyName(Integer.parseInt(currencyId));
                } else {
                    currencyName = "na";
                }
                String currencies = currencyName + "_" + getReferenceCurrency();
                String json = getExchangeRateFromAPI(currencies);
                ExchangeRateForm form = new ExchangeRateForm();
                form.setId(req.getParameter("id"));
                form.setCurrencyId(currencyId);
                form.setCurrentExchangeRate(req.getParameter("currentExchangeRate"));
                form.setCurrencies(CurrencyDAO.getInstance().getCurrencies());
                if (json.length() > 0 && !"{}".equals(json))
                {
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject rate = (JSONObject)parser.parse(json);
                        String currentRate = rate.get(currencies).toString();
                        form.setCurrentExchangeRate(currentRate);
                    } catch (ParseException ex) {
                        throw new AppException(ex.getMessage());
                    }
                }
                try {
                    req.setAttribute("formData", form);
                    RequestDispatcher rd = 
                        req.getRequestDispatcher("/WEB-INF/exchange-rates/form.jsp");
                    rd.forward(req, res);
                } catch (ServletException | IOException ex) {
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