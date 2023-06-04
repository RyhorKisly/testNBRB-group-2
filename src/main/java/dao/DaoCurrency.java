package dao;

import core.Currency;
import dao.api.IDaoCurrency;
import dao.ds.api.IDataSourceWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCurrency implements IDaoCurrency {
    IDataSourceWrapper iDataSourceWrapper;

    private static final String GET_CURRENCIES = "SELECT id, parent_id, code, abbreviation, name, " +
            "name_bel, name_eng, quot_name, quot_name_bel, quot_name_eng, name_multi, name_bel_multi, " +
            "name_eng_multi, scale, periodicity, date_start, date_end \n" +
            "FROM curr.currency;";
    private static final String GET_CURRENCIES_BY_ID = "SELECT id, parent_id, code, abbreviation, name, " +
            "name_bel, name_eng, quot_name, quot_name_bel, quot_name_eng, name_multi, name_bel_multi, " +
            "name_eng_multi, scale, periodicity, date_start, date_end \n" +
            "FROM curr.currency \n" +
            "WHERE abbreviation = ?;";
    private static final String SAVE_CURRENCIES = "INSERT INTO curr.currency(\n" +
            "\tid, parent_id, code, abbreviation, name, name_bel, name_eng, quot_name, " +
            "quot_name_bel, quot_name_eng, name_multi, name_bel_multi, name_eng_multi, " +
            "scale, periodicity, date_start, date_end) \n" +
            "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_CURRENCY = "DELETE FROM curr.currency \n" +
            "\tWHERE id = ?;";
    public DaoCurrency(IDataSourceWrapper iDataSourceWrapper) {
        this.iDataSourceWrapper = iDataSourceWrapper;
    }

    @Override
    public void saveCurrency(List<Currency> currencies) {
        try (
                Connection conn = iDataSourceWrapper.getConnection()
        ) {
            for (Currency currency : currencies) {
                PreparedStatement ps = conn.prepareStatement(SAVE_CURRENCIES);
                ps.setObject(1, currency.getId());
                ps.setObject(2, currency.getParentId());
                ps.setObject(3, currency.getCode());
                ps.setObject(4, currency.getAbbreviation());
                ps.setObject(5, currency.getName());
                ps.setObject(6, currency.getNameBel());
                ps.setObject(7, currency.getNameEng());
                ps.setObject(8, currency.getQuotName());
                ps.setObject(9, currency.getQuotNameBel());
                ps.setObject(10, currency.getQuotNameEng());
                ps.setObject(11, currency.getNameMulti());
                ps.setObject(12, currency.getNameBelMulti());
                ps.setObject(13, currency.getNameEngMulti());
                ps.setObject(14, currency.getScale());
                ps.setObject(15, currency.getPeriodicity());
                ps.setObject(16, java.sql.Date.valueOf(currency.getDateStart().toLocalDate()));
                ps.setObject(17, java.sql.Date.valueOf(currency.getDateEnd().toLocalDate()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    @Override
    public List<Currency> getCurrency() {
        List<Currency> currencies= new ArrayList<>();
        try (Connection conn = iDataSourceWrapper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(GET_CURRENCIES)) {
                while (rs.next()) {
                    Currency currency = new Currency();
                    currency.setId(rs.getLong("id"));
                    currency.setParentId(rs.getLong("parent_id"));;
                    currency.setCode(rs.getLong("code"));
                    currency.setAbbreviation(rs.getString("abbreviation"));
                    currency.setName(rs.getString("name"));
                    currency.setNameBel(rs.getString("name_bel"));
                    currency.setNameEng(rs.getString("name_eng"));
                    currency.setQuotName(rs.getString("quot_name"));
                    currency.setQuotNameBel(rs.getString("quot_name_bel"));
                    currency.setQuotNameEng(rs.getString("quot_name_eng"));
                    currency.setNameMulti(rs.getString("name_multi"));
                    currency.setNameBelMulti(rs.getString("name_bel_multi"));
                    currency.setNameEngMulti(rs.getString("name_eng_multi"));
                    currency.setScale(rs.getLong("scale"));
                    currency.setPeriodicity(rs.getLong("periodicity"));
                    currency.setDateStart(rs.getDate("date_start").toLocalDate().atStartOfDay());
                    currency.setDateEnd(rs.getDate("date_end").toLocalDate().atStartOfDay());
                    currencies.add(currency);

                }
            } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
        return currencies;
    }

    @Override
    public List<Currency> getCurrency(String abbreviation) {
        List<Currency> currencies= new ArrayList<>();
        try (Connection conn = iDataSourceWrapper.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_CURRENCIES_BY_ID)) {
            ps.setObject(1, abbreviation);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Currency currency = new Currency();
                    currency.setId(rs.getLong("id"));
                    currency.setParentId(rs.getLong("parent_id"));;
                    currency.setCode(rs.getLong("code"));
                    currency.setAbbreviation(rs.getString("abbreviation"));
                    currency.setName(rs.getString("name"));
                    currency.setNameBel(rs.getString("name_bel"));
                    currency.setNameEng(rs.getString("name_eng"));
                    currency.setQuotName(rs.getString("quot_name"));
                    currency.setQuotNameBel(rs.getString("quot_name_bel"));
                    currency.setQuotNameEng(rs.getString("quot_name_eng"));
                    currency.setNameMulti(rs.getString("name_multi"));
                    currency.setNameBelMulti(rs.getString("name_bel_multi"));
                    currency.setNameEngMulti(rs.getString("name_eng_multi"));
                    currency.setScale(rs.getLong("scale"));
                    currency.setPeriodicity(rs.getLong("periodicity"));
                    currency.setDateStart(rs.getDate("date_start").toLocalDate().atStartOfDay());
                    currency.setDateEnd(rs.getDate("date_end").toLocalDate().atStartOfDay());
                    currencies.add(currency);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
        return currencies;
    }

    @Override
    public void remove(long id) {
        try (Connection connection = iDataSourceWrapper.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CURRENCY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }    }
}
