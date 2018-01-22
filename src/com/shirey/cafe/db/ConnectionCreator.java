package com.shirey.cafe.db;

import com.shirey.cafe.manager.DatabaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionCreator {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionCreator.class);
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            LOGGER.log(Level.TRACE, "database driver was registered successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "database driver cant't be registered, throwing RuntimeException.", e);
            throw new RuntimeException("database driver cant't be registered.", e);
        }
    }

    private ConnectionCreator() {
    }

    static Connection createConnection() {

        String url = DatabaseManager.getProperty(URL_KEY);
        String user = DatabaseManager.getProperty(USER_KEY);
        String password = DatabaseManager.getProperty(PASSWORD_KEY);

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            LOGGER.log(Level.TRACE, "new connection to database was created successfully.");
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "connection to database  can't be established, check properties or database settings.", e);
            throw new RuntimeException("connection to database can't be established, check properties or database settings.", e);
        }
    }


}
