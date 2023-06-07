package controllerrs;

import core.Currency;
import jakarta.servlet.annotation.WebServlet;
import servise.fabric.ServiceCurrencySingleton;
import servise.api.IServiceCurrency;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/typeCur")
public class CurrencyServlet extends HttpServlet {

    private IServiceCurrency serviceCurrency;

    @Override
    public void init() throws ServletException {
        super.init();
        serviceCurrency = ServiceCurrencySingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type == null || type.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Currency type is missing");
            return;
        }

        try {
            List<Currency> currencies = serviceCurrency.getCurrencyByType(type);
            req.setAttribute("currencies", currencies);
            req.getRequestDispatcher("/currency.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid currency type");
        }
    }
}