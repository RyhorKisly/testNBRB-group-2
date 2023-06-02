package by.it_academy.jd2.Mk_JD2_98_23.gr2.dao;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.CurrencyDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.dao.connection.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrencyDao {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyDao.class);

    public static final String ADD_NEW_CURRENCY_SQL = "INSERT INTO currencies (cur_id, cur_parent_id, cur_code, " +
            "cur_abbreviation, cur_name, cur_name_bel, cur_name_eng, cur_quot_name, cur_quot_name_bel, " +
            "cur_quot_name_eng, cur_name_multi, cur_name_bel_multi, cur_name_eng_multi, cur_scale, cur_periodicity, " +
            "cur_date_start, cur_date_end) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_CURRENCIES_SQL = "SELECT * FROM currencies";

    public List<CurrencyDto> addCurrencies(List<CurrencyDto> currencies) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(ADD_NEW_CURRENCY_SQL)) {
            for (CurrencyDto currency : currencies) {
                stmt.setInt(1, currency.getCurId());
                stmt.setInt(2, currency.getCurParentId());
                stmt.setString(3, currency.getCurCode());
                stmt.setString(4, currency.getCurAbbreviation());
                stmt.setString(5, currency.getCurName());
                stmt.setString(6, currency.getCurNameBel());
                stmt.setString(7, currency.getCurNameEng());
                stmt.setString(8, currency.getCurQuotName());
                stmt.setString(9, currency.getCurQuotNameBel());
                stmt.setString(10, currency.getCurQuotNameEng());
                stmt.setString(11, currency.getCurNameMulti());
                stmt.setString(12, currency.getCurNameBelMulti());
                stmt.setString(13, currency.getCurNameEngMulti());
                stmt.setInt(14, currency.getCurScale());
                stmt.setInt(15, currency.getCurPeriodicity());
                stmt.setString(16, currency.getCurDateStart());
                stmt.setString(17, currency.getCurDateEnd());

                stmt.addBatch();
            }

            int[] rowsInserted = stmt.executeBatch();
            logger.debug("Inserted {} currencies into the database.", rowsInserted.length);
            return currencies;
        } catch (SQLException ex) {
            logger.error("Error occurred while inserting currencies into the database.", ex);
        }
        return Collections.emptyList();
    }

    public List<CurrencyDto> getAllCurrencies() {
        List<CurrencyDto> currencies = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_CURRENCIES_SQL)) {
            while (rs.next()) {
                CurrencyDto currency = new CurrencyDto();
                currency.setCurId(rs.getInt("cur_id"));
                currency.setCurParentId(rs.getInt("cur_parent_id"));
                currency.setCurCode(rs.getString("cur_code"));
                currency.setCurAbbreviation(rs.getString("cur_abbreviation"));
                currency.setCurName(rs.getString("cur_name"));
                currency.setCurNameBel(rs.getString("cur_name_bel"));
                currency.setCurNameEng(rs.getString("cur_name_eng"));
                currency.setCurQuotName(rs.getString("cur_quot_name"));
                currency.setCurQuotNameBel(rs.getString("cur_quot_name_bel"));
                currency.setCurQuotNameEng(rs.getString("cur_quot_name_eng"));
                currency.setCurNameMulti(rs.getString("cur_name_multi"));
                currency.setCurNameBelMulti(rs.getString("cur_name_bel_multi"));
                currency.setCurNameEngMulti(rs.getString("cur_name_eng_multi"));
                currency.setCurScale(rs.getInt("cur_scale"));
                currency.setCurPeriodicity(rs.getInt("cur_periodicity"));
                currency.setCurDateStart(rs.getString("cur_date_start"));
                currency.setCurDateEnd(rs.getString("cur_date_end"));

                currencies.add(currency);
                logger.debug("Retrieved a currency: {}", currency);
            }
        } catch (SQLException ex) {
            logger.error("Error occurred while retrieving currencies.", ex);
        }

        return currencies;
    }

}
