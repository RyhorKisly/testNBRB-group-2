package controllerrs;

import core.StatisticCurrency;
import servise.api.IServiceSend;
import servise.api.IServiceStatistic;
import servise.fabric.ServiceSendSingleton;
import servise.fabric.ServiceStatisticSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class StatisticServlet extends HttpServlet {
    private final IServiceSend serviceSend;
    private final IServiceStatistic serviceStatistic;

    public StatisticServlet() {
        this.serviceSend = ServiceSendSingleton.getInstance();
        this.serviceStatistic = ServiceStatisticSingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем тип валюты и даты начала и конца периода из параметров запроса
        String type = req.getParameter("type");
        if (type == null || type.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан тип валюты");
            return;
        }

        String dateStartStr = req.getParameter("dateStart");
        String dateEndStr = req.getParameter("dateEnd");
        LocalDate dateStart;
        LocalDate dateEnd;
        try {
            dateStart = LocalDate.parse(dateStartStr);
            dateEnd = LocalDate.parse(dateEndStr);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат даты");
            return;
        }

        // Получаем статистику о валюте за указанный период
        List<StatisticCurrency> statisticCurrencies = serviceStatistic.getCurrency(type, dateStart, dateEnd);

        // Отправляем статистику о валюте в формате JSON
        resp.setContentType("application/json");
        resp.getWriter().write(statisticCurrencies.toString());
    }
}
