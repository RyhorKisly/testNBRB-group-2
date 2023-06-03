package dao.ds;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import dao.ds.api.IDataSourceWrapper;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceC3P0 implements IDataSourceWrapper {
    private ComboPooledDataSource ds;

    public DataSourceC3P0() throws PropertyVetoException {
        this.ds = new ComboPooledDataSource();
        this.ds = new ComboPooledDataSource();
        this.ds.setDriverClass("org.postgresql.Driver");
        this.ds.setJdbcUrl("jdbc:postgresql://localhost:5432/curr");
        this.ds.setUser("postgres");
        this.ds.setPassword("postgres");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }


    @Override
    public void close() throws Exception {
        this.ds.close();
    }
}
