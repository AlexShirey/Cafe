package com.shirey.cafe.db;

import com.shirey.cafe.manager.DatabaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code ConnectionCreator} class
 * has only one static method that creates connection to
 * database using {@code DatabaseManager} class.
 * <p>
 * This class has package-private access level to prevent
 * create connections outside this package.
 *
 * @author Alex Shirey
 * @see DatabaseManager
 */

class ConnectionCreator {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionCreator.class);
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

    /*
     * Registering driver to Mysql server
     */
    static {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            LOGGER.log(Level.TRACE, "database driver was registered successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "database driver cant't be registered, throwing RuntimeException.", e);
            throw new RuntimeException("database driver cant't be registered.", e);
        }
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private ConnectionCreator() {
    }

    /**
     * Attempts to establish a connection to the database
     * using params (url, user and password) from the properties file.
     *
     * @return a {@code Connection} to the database
     * @throws RuntimeException if no resource bundle or no object for the given key can be found,
     *                          if a database access error occurs or the url in the properties file is
     *                          {@code null}
     */
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
