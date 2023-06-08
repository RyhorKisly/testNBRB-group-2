package controllerrs;

import servise.api.IServiceStatistic;
import servise.fabric.ServiceStatisticSingleton;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AGVCurrency", urlPatterns = "/avg")
public class AGVCurrency extends HttpServlet {
    private final IServiceStatistic serviceStatistic;

    public AGVCurrency() {
        this.serviceStatistic = ServiceStatisticSingleton.getInstance();
    }

    @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String typeCurrency = request.getParameter("type");
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");
        if(monthStr==null||monthStr.isBlank()){
            throw new IllegalArgumentException("Не введен месяц");
        }
        if(yearStr==null||monthStr.isBlank()){
            throw new IllegalArgumentException("Не введен год");
        }
        if(typeCurrency==null||monthStr.isBlank()){
            throw new IllegalArgumentException("Не введена валюта");
        }
        int month = Integer.parseInt(monthStr);
        int year = Integer.parseInt(yearStr);

        try {
        // Преобразуем даты из строкового формата в объекты LocalDate
        // Получаем статистику валюты по заданным параметрам
        // Преобразуем статистику в формат JSON
        // Отправляем JSON-ответ клиенту
        response.getWriter().println(serviceStatistic.getAvgCurrency(typeCurrency,month,year));
        } catch (IllegalArgumentException e) {
        // Если возникла ошибка, возвращаем сообщение об ошибке
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Error: " + e.getMessage());
        }
        }
}