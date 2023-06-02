package by.it_academy.jd2.Mk_JD2_98_23.gr2.dao;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.RateDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.dao.connection.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RateDao {

    private static final Logger logger = LoggerFactory.getLogger(RateDao.class);

    private static final String ADD_NEW_RATE_SQL = "INSERT INTO rates (cur_id, date, cur_abbreviation, " +
            "cur_scale, cur_name, cur_official_rate) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String GET_ALL_RATES_SQL = "SELECT * FROM rates";

    public List<RateDto> addRates(List<RateDto> rates) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(ADD_NEW_RATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            for (RateDto rate : rates) {
                stmt.setInt(1, rate.getCurId());
                stmt.setDate(2, Date.valueOf(rate.getDate()));
                stmt.setString(3, rate.getCurAbbreviation());
                stmt.setInt(4, rate.getCurScale());
                stmt.setString(5, rate.getCurName());
                stmt.setDouble(6, rate.getCurOfficialRate());

                stmt.addBatch();
            }

            int[] rowsInserted = stmt.executeBatch();
            logger.debug("Inserted {} rates into the database.", rowsInserted.length);

            return rates;
        } catch (SQLException ex) {
            logger.error("Error occurred while inserting rates into the database.", ex);
        }

        return Collections.emptyList();
    }


    public List<RateDto> getAllRates() {
        List<RateDto> rates = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_RATES_SQL)) {

            while (rs.next()) {
                RateDto rate = new RateDto();
                rate.setCurId(rs.getInt("cur_id"));
                rate.setDate(rs.getObject("date", LocalDate.class));
                rate.setCurAbbreviation(rs.getString("cur_abbreviation"));
                rate.setCurScale(rs.getInt("cur_scale"));
                rate.setCurName(rs.getString("cur_name"));
                rate.setCurOfficialRate(rs.getDouble("cur_official_rate"));
                rates.add(rate);
            }

            logger.info("All rates retrieved successfully!");
        } catch (SQLException e) {
            logger.error("Error occurred while retrieving rates from database.", e);
        }

        return rates;
    }

}
