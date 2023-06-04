package dao;

import com.sun.source.tree.LiteralTree;
import core.Currency;
import dao.api.IDaoCurrency;
import dao.ds.DataSourceC3P0;
import dao.ds.api.IDataSourceWrapper;
import dao.ds.fabric.DataSourceSingleton;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoCurrency implements IDaoCurrency {
    IDataSourceWrapper iDataSourceWrapper;
    private static final String GET_ID = "SELECT id \n" +
            "FROM curr.currency \n" +
            "WHERE abbreviation = ?;";
    private static final String SAVE_CURRENCIES = "INSERT INTO curr.currency(\n" +
            "\tid, parent_id, code, abbreviation, name, name_bel, name_eng, quot_name, " +
            "quot_name_bel, quot_name_eng, name_multi, name_bel_multi, name_eng_multi, " +
            "scale, periodicity, date_start, date_end) \n" +
            "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public DaoCurrency(IDataSourceWrapper iDataSourceWrapper) {
        this.iDataSourceWrapper = iDataSourceWrapper;
    }

    @Override
    public void saveCurrency(List<Currency> currencies) {
        try (
                Connection conn = DataSourceSingleton.getInstance().getConnection()
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
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exist(String typeCurrency) {
        return false;
    }

    @Override
    public List<Currency> getCurrency(String abbreviation) {

        return null;
    }

    @Override
    public void remove(long id) {

    }
}
