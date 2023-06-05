package controllerrs;

import servise.api.IServiceCurrency;
import servise.fabric.ServiceCurrencySingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCurrencyServlet extends HttpServlet {
    private final IServiceCurrency serviceCurrency;

    public UpdateCurrencyServlet() {
        this.serviceCurrency = ServiceCurrencySingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Вызываем метод для обновления данных о валютах
        serviceCurrency.updateCurrency();

        // Возвращаем успешный статус
        resp.getWriter().println("Currency update complete.");
    }
}
