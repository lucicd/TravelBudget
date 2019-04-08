package lucicd.travelbudget.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lucicd.travelbudget.exceptions.AppException;

@WebServlet(name = "Weather", urlPatterns = {"/weather"})
public class WeatherServlet extends HttpServlet {
    
    private String getForecast(String city)
            throws AppException
    {
        String result = "";
        URL url;
        String proxyAddress = System.getenv("PROXY_ADDRESS");
        String proxyPort = System.getenv("PROXY_PORT");
        Proxy proxy = null;
        if (proxyAddress != null && proxyPort != null) {
            proxy = new Proxy(Proxy.Type.HTTP, 
                    new InetSocketAddress(proxyAddress, Integer.parseInt(proxyPort)));
        }
        try {
            url = new URL("https://api.openweathermap.org/data/2.5/weather?q="
                    + city
                    + "&units=metric"
                    + "&APPID=4d59cc2b9225c1b711f9635498dab122");
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
                throw new AppException("Unknown host: " + ex.getMessage());    
            }
        } catch (MalformedURLException ex) {
            throw new AppException("Malformed URL: " + ex.getMessage());
        } catch (IOException ex) {
            throw new AppException("IO Exception: " + ex.getMessage());
        }
        
        return result;
    }

    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) 
            throws ServletException, IOException
    {
        try {
            String city = req.getParameter("city");
            String json = getForecast(city);
            System.out.println(json);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);
        } catch (AppException ex) {
            req.setAttribute("error", ex.getMessage());
            String url = "/WEB-INF/shared/error.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(url);
            dispatcher.forward(req, res);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }
}
