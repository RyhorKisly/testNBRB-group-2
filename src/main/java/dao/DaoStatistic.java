package dao;

import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import dao.ds.api.IDataSourceWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoStatistic implements IDaoStatisticCurrency {
    private final String SQL_SAVE = "INSERT INTO curr.statistic " +
            "(id, date_curr, abbreviation, scale, name, official_rate) VALUES (?,?,?,?,?,?);";
    private final String SQL_GET = "SELECT id, date_curr, abbreviation, scale, name, official_rate " +
            "FROM curr.statistic WHERE (id = (?));";
    private final String SQL_GET_DATE = "SELECT id, date_curr, abbreviation, scale, name, official_rate " +
            "FROM curr.statistic " +
            "WHERE ((?,?)OVERLAPS(date_curr,date_curr)) AND id = ? ORDER BY date_curr;";

    private final IDataSourceWrapper wrapper;

    public DaoStatistic(IDataSourceWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void saveStatisticCurrency(List<StatisticCurrency> statisticCurrencies) {
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE);
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
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET);
            preparedStatement.setLong(1,typeCurrency);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                LocalDateTime date = resultSet.getDate("date_curr").toLocalDate().atStartOfDay();
                String abbreviation = resultSet.getString("abbreviation");
                long scale = resultSet.getLong("scale");
                String name = resultSet.getString("name");
                double officialRate = resultSet.getDouble("official_rate");
                statisticCurrencies.add(new StatisticCurrency(
                        id,date, abbreviation,scale,name, officialRate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }

    @Override
    public List<StatisticCurrency> getCurrency(long typeCurrency, LocalDate dateStart, LocalDate dateEnd) {
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_DATE);
            preparedStatement.setDate(1,
                    java.sql.Date.valueOf(dateStart));
            preparedStatement.setDate(2,
                    java.sql.Date.valueOf(dateEnd));
            preparedStatement.setLong(3,typeCurrency);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                LocalDateTime date = resultSet.getDate("date_curr").toLocalDate().atStartOfDay();
                String abbreviation = resultSet.getString("abbreviation");
                long scale = resultSet.getLong("scale");
                String name = resultSet.getString("name");
                long officialRate = resultSet.getLong("official_rate");
                statisticCurrencies.add(new StatisticCurrency(
                        id,date, abbreviation,scale,name, officialRate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }

}
