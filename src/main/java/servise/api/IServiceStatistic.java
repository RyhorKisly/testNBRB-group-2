package servise.api;

import core.StatisticCurrency;

import java.time.LocalDate;
import java.util.List;

public interface IServiceStatistic {
    List<StatisticCurrency> getCurrency(String typeCurrency, LocalDate dateStart, LocalDate dateEnd);
    List<StatisticCurrency> getCurrency(String typeCurrency);
    double getAvgCurrency(String typeCurrency, int monthMM, int yearYYYY);
}
