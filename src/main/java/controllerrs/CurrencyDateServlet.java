package controllerrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.StatisticCurrency;
import servise.ServiceStatistic;
import servise.api.IServiceStatistic;
import servise.fabric.ServiceStatisticSingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

//1
@WebServlet(name = "CurrencyDateServlet", urlPatterns = "/statistic")
public class CurrencyDateServlet extends HttpServlet {
    private final IServiceStatistic serviceStatistic;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String PARAM_TYPE_CURRENCY = "type";
    private final String PARAM_DATE_START = "dateStart";
    private final String PARAM_DATE_END = "dateEnd";
    public CurrencyDateServlet() {
        this.serviceStatistic = ServiceStatisticSingleton.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        Writer writer = resp.getWriter();
        Map<String, String[]> parametrMap = req.getParameterMap();
        try {
            String[] type = parametrMap.get(PARAM_TYPE_CURRENCY);
            if(type==null)
                throw new IllegalArgumentException("Не введен тип валюты");
            if(type.length==0)
                throw new IllegalArgumentException("Не введен тип валюты");
            if(type[0]==null)
                throw new IllegalArgumentException("Не введен тип валюты");

            String[] dateStartString = parametrMap.get(PARAM_DATE_START);
            if(dateStartString==null)
                throw new IllegalArgumentException("Не введена начальная дата");
            if(dateStartString.length==0)
                throw new IllegalArgumentException("Не введена начальная дата");
            if(dateStartString[0]==null)
                throw new IllegalArgumentException("Не введена начальная дата");
            LocalDate dateStart = LocalDate.parse(dateStartString[0]);


            String[] dateEndString = parametrMap.get(PARAM_DATE_END);
            if(dateEndString==null)
                throw new IllegalArgumentException("Не введена конечная дата");
            if(dateEndString.length==0)
                throw new IllegalArgumentException("Не введена начальная дата");
            if(dateEndString[0]==null)
                throw new IllegalArgumentException("Не введена начальная дата");
            LocalDate dateEnd = LocalDate.parse(dateEndString[0]);
            List<StatisticCurrency> statisticCurrency = serviceStatistic.getCurrency(type[0],dateStart,dateEnd);
            writer.write(mapper.writeValueAsString(statisticCurrency));
        }catch (RuntimeException e){
            writer.write(e.getMessage());
        }
    }
}
