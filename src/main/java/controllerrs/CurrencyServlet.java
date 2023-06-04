package controllerrs;

import core.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servise.api.IServiceCurrency;
import servise.fabric.ServiceCurrencySingleton;

import java.io.IOException;

public class CurrencyServlet extends HttpServlet {
    private final IServiceCurrency serviceCurrency;

    public CurrencyServlet() {
        this.serviceCurrency = ServiceCurrencySingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем тип валюты из параметра запроса
        String type = req.getParameter("type");
        if (type == null || type.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан тип валюты");
            return;
        }

        // Получаем информацию о валюте
        Currency currency = serviceCurrency.getCurrency(type);
        if (currency == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта не найдена");
            return;
        }

        // Отправляем информацию о валюте в формате JSON
        resp.setContentType("application/json");
        resp.getWriter().write(currency.toString());
    }
}