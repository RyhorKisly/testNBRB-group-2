package controllerrs;

import servise.api.IServiceCurrency;
import servise.fabric.ServiceCurrencySingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CurrencyServlet", urlPatterns = "/update")
public class CurrencyServlet extends HttpServlet {
    private final IServiceCurrency serviceCurrency;

    public CurrencyServlet() {
        this.serviceCurrency = ServiceCurrencySingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
