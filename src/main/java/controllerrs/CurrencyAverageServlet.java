package controllerrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import servise.api.IServiceStatistic;
import servise.fabric.ServiceStatisticSingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

//3
@WebServlet(name = "CurrencyAverageServlet", urlPatterns = "/avg")
public class CurrencyAverageServlet extends HttpServlet {
    private final IServiceStatistic serviceStatistic;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String PARAM_TYPE_CURRENCY = "param";
    private final String PARAM_TYPE_MONTH = "month";
    private final String PARAM_TYPE_YEAR = "year";

    public CurrencyAverageServlet() {
        this.serviceStatistic = ServiceStatisticSingleton.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        Map<String, String[]>  parametrMap = req.getParameterMap();
        Writer writer = resp.getWriter();
        try {
            String[] type = parametrMap.get(PARAM_TYPE_CURRENCY);
            if(type==null)
                throw new IllegalArgumentException("Не введен тип валюты");
            if(type.length==0)
                throw new IllegalArgumentException("Не введен тип валюты");
            if(type[0]==null)
                throw new IllegalArgumentException("Не введен тип валюты");

            String[] monthStr = parametrMap.get(PARAM_TYPE_MONTH);
            if(monthStr==null)
                throw new IllegalArgumentException("Не введен месяц");
            if(monthStr.length==0)
                throw new IllegalArgumentException("Не введен месяц");
            if(monthStr[0]==null)
                throw new IllegalArgumentException("Не введен месяц");
            int month = Integer.parseInt(monthStr[0]);
            if(month<1||month>12)
                throw new IllegalArgumentException("Не верно введен месяц");

            String[] yearStr = parametrMap.get(PARAM_TYPE_YEAR);
            if(yearStr==null)
                throw new IllegalArgumentException("Не введен год");
            if(yearStr.length==0)
                throw new IllegalArgumentException("Не введен год");
            if(yearStr[0]==null)
                throw new IllegalArgumentException("Не введен год");
            int year = Integer.parseInt(yearStr[0]);
            double avg = serviceStatistic.getAvgCurrency(type[0],month,year);
            writer.write(mapper.writeValueAsString(avg));
        } catch (RuntimeException e){
            writer.write(e.getMessage());
        }
    }
}
