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

public class DaoStatisticCurrency implements IDaoStatisticCurrency {
    IDataSourceWrapper dataSourceWrapper;
    private final static String SAVE_STATISTIC_CURRENCY = "INSERT INTO curr.statistic(" +
            "id, date_curr, abbreviation, scale, name, official_rate) \n" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private final static String GET_CURRENCIES_BY_ID = "SELECT " +
            "id, date_curr, abbreviation, scale, name, official_rate \n" +
            "FROM curr.statistic \n" +
            "WHERE id = ?;";
    private final static String GET_CURRENCIES_BY_DATES_AND_ID = "SELECT " +
            "id, date_curr, abbreviation, scale, name, official_rate \n" +
            "FROM curr.statistic \n" +
            "WHERE date_curr >= ? AND date_curr <= ? AND id = ?;";
    private final static String GET_CURRENCIES_BY_MONTH_AND_ID_WITHOUT_WEEKENDS = "SELECT " +
            "id, date_curr, abbreviation, scale, name, official_rate " +
            "FROM curr.statistic " +
            "WHERE id = ? " +
            "AND EXTRACT(MONTH FROM date_curr) = ? " +
            "AND EXTRACT(YEAR FROM date_curr) = ? " +
            "AND date_curr = (SELECT calendar_date " +
            "FROM curr.weekends " +
            "WHERE is_day_off = '0' AND calendar_date = date_curr);";




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
    public List<StatisticCurrency> getCurrency(long curId) {
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        try (Connection conn = dataSourceWrapper.getConnection()){
            PreparedStatement ps = conn.prepareStatement(GET_CURRENCIES_BY_ID);
            ps.setLong(1, curId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id");
                LocalDateTime date = rs.getDate("date_curr").toLocalDate().atStartOfDay();
                String abbreviation = rs.getString("abbreviation");
                long scale = rs.getLong("scale");
                String name = rs.getString("name");
                long officialRate = rs.getLong("official_rate");
                statisticCurrencies.add(new StatisticCurrency(
                        id,date, abbreviation,scale,name, officialRate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }

    @Override
    public List<StatisticCurrency> getCurrencyFrom(long curId, LocalDate dateStart, LocalDate dateEnd) {
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        try (Connection conn = dataSourceWrapper.getConnection()){
            PreparedStatement ps = conn.prepareStatement(GET_CURRENCIES_BY_DATES_AND_ID);
            ps.setDate(1,
                    java.sql.Date.valueOf(dateStart));
            ps.setDate(2,
                    java.sql.Date.valueOf(dateEnd));
            ps.setLong(3,curId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id");
                LocalDateTime date = rs.getDate("date_curr").toLocalDate().atStartOfDay();
                String abbreviation = rs.getString("abbreviation");
                long scale = rs.getLong("scale");
                String name = rs.getString("name");
                long officialRate = rs.getLong("official_rate");
                statisticCurrencies.add(new StatisticCurrency(
                        id,date, abbreviation,scale,name, officialRate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }

    @Override
    public List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long curId, int month, int year) {
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        try (Connection conn = dataSourceWrapper.getConnection()){
            PreparedStatement ps = conn.prepareStatement(GET_CURRENCIES_BY_MONTH_AND_ID_WITHOUT_WEEKENDS);
            ps.setLong(1, curId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id");
                LocalDateTime date = rs.getDate("date_curr").toLocalDate().atStartOfDay();
                String abbreviation = rs.getString("abbreviation");
                long scale = rs.getLong("scale");
                String name = rs.getString("name");
                long officialRate = rs.getLong("official_rate");
                statisticCurrencies.add(new StatisticCurrency(
                        id,date, abbreviation,scale,name, officialRate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }
}
