package servise.api;

import core.Currency;
import core.StatisticCurrency;

import java.time.LocalDate;
import java.util.List;

public interface IServiceSend {
    List<StatisticCurrency> sendGetDynamics(long idCurrency, LocalDate dateStart, LocalDate dateEnd);
    List<Currency> getCurrency();
}
