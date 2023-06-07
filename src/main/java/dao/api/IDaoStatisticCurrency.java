package dao.api;

import core.StatisticCurrency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IDaoStatisticCurrency {
    void saveStatisticCurrency(List<StatisticCurrency> statisticCurrencies);
    List<StatisticCurrency> getCurrency(long curId);
    List<StatisticCurrency> getCurrencyFrom(long curId, LocalDate dateStart, LocalDate dateEnd);
    List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long curId, int month, int year);

}
