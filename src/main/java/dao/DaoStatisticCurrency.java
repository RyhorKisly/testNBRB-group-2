package dao;

import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import dao.ds.api.IDataSourceWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoStatisticCurrency implements IDaoStatisticCurrency {
    IDataSourceWrapper dataSourceWrapper;
    private final static String SAVE_STATISTIC_CURRENCY = "INSERT INTO curr.statistic(" +
            "id, date_curr, abbreviation, scale, name, official_rate) \n" +
            "VALUES (?, ?, ?, ?, ?, ?);";

    public DaoStatisticCurrency(IDataSourceWrapper dataSourceWrapper) {
        this.dataSourceWrapper = dataSourceWrapper;
    }

    @Override
    public void saveStatisticCurrency(List<StatisticCurrency> statisticCurrencies) {
        try (Connection connection = dataSourceWrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_STATISTIC_CURRENCY);
            for(StatisticCurrency statisticCurrency : statisticCurrencies){
                preparedStatement.setLong(1,statisticCurrency.getId());
                preparedStatement.setDate(2,
                        java.sql.Date.valueOf(statisticCurrency.getDate().toLocalDate()));
                preparedStatement.setString(3,statisticCurrency.getAbbreviation());
                preparedStatement.setLong(4,statisticCurrency.getScale());
                preparedStatement.setString(5,statisticCurrency.getName());
                preparedStatement.setDouble(6,statisticCurrency.getOfficialRate());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StatisticCurrency> getCurrency(long typeCurrency) {
        return null;
    }

    @Override
    public List<StatisticCurrency> getCurrencyFrom(long typeCurrency, LocalDate dateStart, LocalDate dateEnd) {
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        try (Connection conn = dataSourceWrapper.getConnection()){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }

    @Override
    public List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long typeCurrency, int month) {
        return null;
    }
}
