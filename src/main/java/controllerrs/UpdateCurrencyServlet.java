package controllerrs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servise.api.IServiceCurrency;
import servise.fabric.ServiceCurrencySingleton;


import java.io.IOException;
@WebServlet(urlPatterns = "/updateCur")
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
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
