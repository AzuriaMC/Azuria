package fr.choukas.azuria.api.data.sql;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseAccess {

    private DatabaseCredentials credentials;
    private BasicDataSource dataSource;

    public DatabaseAccess(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }

    public void initPool() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(this.credentials.getUsername());
        dataSource.setPassword(this.credentials.getPassword());
        dataSource.setUrl(this.credentials.toURI());

        this.dataSource = dataSource;
    }

    public void closePool() {
        try {
            this.dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            this.initPool();
        }

        return this.dataSource.getConnection();
    }
}
