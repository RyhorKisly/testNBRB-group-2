package by.it_academy.jd2.Mk_JD2_98_23.gr2.dao.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static Connection connection = null;
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/nbrb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private DatabaseManager() {
        // Private constructor for Singleton
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            logger.info("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            logger.error("Connection to the PostgreSQL server failed.", e);
        }
        return connection;
    }
}
