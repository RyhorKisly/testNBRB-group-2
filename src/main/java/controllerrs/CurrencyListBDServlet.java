package controllerrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.StatisticCurrency;
import servise.api.IServiceStatistic;
import servise.fabric.ServiceStatisticSingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

//2
@WebServlet(name = "CurrencyListBDServlet", urlPatterns = "/db")
public class CurrencyListBDServlet extends HttpServlet {
    private final IServiceStatistic serviceStatistic;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String PARAM_TYPE_CURRENCY = "type";

    public CurrencyListBDServlet() {
        this.serviceStatistic = ServiceStatisticSingleton.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        try {
            Map<String, String[]> parametrMap = req.getParameterMap();
            String[] type = parametrMap.get(PARAM_TYPE_CURRENCY);
            if(type==null)
                throw new IllegalArgumentException("Не введен тип валюты");
            if(type.length==0)
                throw new IllegalArgumentException("Не введен тип валюты");
            if(type[0]==null)
                throw new IllegalArgumentException("Не введен тип валюты");
            List<StatisticCurrency> statisticCurrencies = serviceStatistic.getCurrency(type[0]);
            writer.write(mapper.writeValueAsString(statisticCurrencies));
        } catch (RuntimeException e){
            writer.write(e.getMessage());
        }
    }
}
