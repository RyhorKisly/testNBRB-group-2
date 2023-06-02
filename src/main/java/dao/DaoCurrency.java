package dao;

import core.Currency;
import dao.api.IDaoCurrency;
import dao.ds.api.IDataSourceWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoCurrency implements IDaoCurrency {
    private final IDataSourceWrapper wrapper;
    public DaoCurrency(IDataSourceWrapper wrapper) {
        this.wrapper = wrapper;
    }
    private final String SQL_GET_ID = "SELECT id FROM curr.currency WHERE abbreviation = (?);";
    private final String SQL_GET_SAVE_CURRENCY = "INSERT INTO curr.currency (" +
            "id, parent_id, code, abbreviation, name, name_bel, " +
            "name_eng, quot_name, quot_name_bel, quot_name_eng, " +
            "name_multi, name_bel_multi, name_eng_multi, scale, " +
            "periodicity, date_start, date_end) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private final String SQL_EXIST = "SELECT abbreviation FROM curr.currency " +
            "WHERE abbreviation = ?;";
    private final String SQL_GET_ALL = "SELECT " +
            "id, parent_id, code, abbreviation, name, name_bel, " +
            "name_eng, quot_name, quot_name_bel, quot_name_eng, " +
            "name_multi, name_bel_multi, name_eng_multi, scale, " +
            "periodicity, date_start, date_end " +
            "FROM curr.currency;";
    private final String SQL_DELETE = "DELETE FROM curr.currency WHERE id = ?;";
    @Override
    public long getID(String typeCurrency) {
        long id = 0;
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ID);
            preparedStatement.setString(1,typeCurrency);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public void saveCurrency(List<Currency> currencies) {
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_SAVE_CURRENCY);
            for (Currency currency : currencies) {
                preparedStatement.setLong(1,currency.getId());
                preparedStatement.setLong(2,currency.getParentId());
                preparedStatement.setLong(3,currency.getCode());
                preparedStatement.setString(4,currency.getAbbreviation());
                preparedStatement.setString(5, currency.getName());
                preparedStatement.setString(6,currency.getNameBel());
                preparedStatement.setString(7,currency.getNameEng());
                preparedStatement.setString(8,currency.getQuotName());
                preparedStatement.setString(9,currency.getQuotNameBel());
                preparedStatement.setString(10,currency.getQuotNameEng());
                preparedStatement.setString(11,currency.getNameMulti());
                preparedStatement.setString(12,currency.getNameBelMulti());
                preparedStatement.setString(13, currency.getNameEngMulti());
                preparedStatement.setLong(14,currency.getScale());
                preparedStatement.setLong(15,currency.getPeriodicity());
                preparedStatement.setDate(16,
                        java.sql.Date.valueOf(currency.getDateStart().toLocalDate()));
                preparedStatement.setDate(17,
                        java.sql.Date.valueOf(currency.getDateEnd().toLocalDate()));
                preparedStatement.executeUpdate();
            };
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exist(String typeCurrency) {
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST);
            preparedStatement.setString(1,typeCurrency);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if(resultSet.getString("abbreviation").equals(typeCurrency))
                    return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Currency> getCurrency() {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                long code = resultSet.getLong("code");
                long parentID = resultSet.getLong("parent_id");
                String abbreviation = resultSet.getString("abbreviation");
                String name = resultSet.getString("name");
                String nameBel = resultSet.getString("name_bel");
                String nameEng = resultSet.getString("name_eng");
                String quotName = resultSet.getString("quot_name");
                String quotNameBel = resultSet.getString("quot_name_bel");
                String quotNameEng = resultSet.getString("quot_name_eng");
                String nameMulti = resultSet.getString("name_multi");
                String nameBelMulti = resultSet.getString("name_bel_multi");
                String nameEngMulti = resultSet.getString("name_eng_multi");
                long scale = resultSet.getLong("scale");
                long periodicity = resultSet.getLong("periodicity");
                LocalDate dateStart = resultSet.getDate("date_start").toLocalDate();
                LocalDate dateEnd = resultSet.getDate("date_end").toLocalDate();
                Currency currency = new Currency(id, parentID, code, abbreviation, name,
                        nameBel, nameEng, quotName, quotNameBel, quotNameEng,
                        nameMulti, nameBelMulti, nameEngMulti, scale, periodicity,
                        dateStart.atStartOfDay(), dateEnd.atStartOfDay());
                currencies.add(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    @Override
    public void remove(long id) {
        try (Connection connection = wrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
