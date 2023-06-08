package controllerrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import core.StatisticCurrency;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servise.api.IServiceStatistic;
import servise.fabric.ServiceStatisticSingleton;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@WebServlet(name = "StatisticServlet", urlPatterns = "/statistic")
public class StatisticServlet extends HttpServlet {
    private IServiceStatistic serviceStatistic;

    public StatisticServlet() {
        this.serviceStatistic = ServiceStatisticSingleton.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeCurrency = request.getParameter("type");
        String startDateString = request.getParameter("start_date");
        String endDateString = request.getParameter("end_date");

        try {
            // Преобразуем даты из строкового формата в объекты LocalDate
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            // Получаем статистику валюты по заданным параметрам
            List<StatisticCurrency> statistics = serviceStatistic.getCurrency(typeCurrency.toUpperCase(), startDate, endDate);

            // Преобразуем статистику в формат JSON
            String json = convertToJson(statistics);

            // Отправляем JSON-ответ клиенту
            response.setContentType("application/json");
            response.getWriter().println(json);
        } catch (IllegalArgumentException e) {
            // Если возникла ошибка, возвращаем сообщение об ошибке
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    private String convertToJson(List<StatisticCurrency> statistics) {
        // Используем ObjectMapper для преобразования списка статистики в JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(statistics);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting statistics to JSON", e);
        }
    }
}
