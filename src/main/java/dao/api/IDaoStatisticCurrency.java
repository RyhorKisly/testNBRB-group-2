package dao.api;

import core.StatisticCurrency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IDaoStatisticCurrency {
    void saveStatisticCurrency(List<StatisticCurrency> statisticCurrencies);
    List<StatisticCurrency> getCurrency(long typeCurrency);
    List<StatisticCurrency> getCurrencyFrom(long typeCurrency, LocalDate dateStart, LocalDate dateEnd);
    List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long typeCurrency, int month);

}
