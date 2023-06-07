package controllerrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Currency;
import servise.api.IServiceCurrency;
import servise.fabric.ServiceCurrencySingleton;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CurrencyServlet", urlPatterns = "/currency")
public class CurrencyServlet extends HttpServlet {
    private final IServiceCurrency serviceCurrency;


    public CurrencyServlet() {
        this.serviceCurrency = ServiceCurrencySingleton.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            // Получаем информацию о валюте
            Currency currency = (Currency) serviceCurrency.getCurrency(typeCurrency);
            if (currency == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта не найдена");
                return;

            }
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