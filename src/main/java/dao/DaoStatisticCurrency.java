package dao;

import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import dao.ds.api.IDataSourceWrapper;

import java.time.LocalDate;
import java.util.List;

public class DaoStatisticCurrency implements IDaoStatisticCurrency {
    IDataSourceWrapper dataSourceWrapper;

    public DaoStatisticCurrency(IDataSourceWrapper dataSourceWrapper) {
        this.dataSourceWrapper = dataSourceWrapper;
    }

    @Override
    public void saveStatisticCurrency(List<StatisticCurrency> statisticCurrencies) {

    }

    @Override
    public List<StatisticCurrency> getCurrency(long typeCurrency) {
        return null;
    }

    @Override
    public List<StatisticCurrency> getCurrencyFrom(long typeCurrency, LocalDate dateStart, LocalDate dateEnd) {
        return null;
    }

    @Override
    public List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long typeCurrency, int month) {
        return null;
    }
}
