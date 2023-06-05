package controllerrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Currency;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servise.api.IServiceCurrency;
import servise.fabric.ServiceCurrencySingleton;

import java.io.IOException;
import java.util.List;

public class CurrencyServlet extends HttpServlet {
    private final IServiceCurrency serviceCurrency;


    public CurrencyServlet() {
        this.serviceCurrency = ServiceCurrencySingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeCurrency = request.getParameter("type");

        try {
            // Получаем список валют по заданному типу
            List<Currency> currencies = serviceCurrency.getCurrency(typeCurrency);

            // Преобразуем список валют в формат JSON
            String json = convertToJson(currencies);

            // Отправляем JSON-ответ клиенту
            response.setContentType("application/json");
            response.getWriter().println(json);
        } catch (IllegalArgumentException e) {
            // Если возникла ошибка, возвращаем сообщение об ошибке
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    private String convertToJson(List<Currency> currencies) {
        // Используем ObjectMapper для преобразования списка валют в JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(currencies);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting currencies to JSON", e);
        }
    }
}