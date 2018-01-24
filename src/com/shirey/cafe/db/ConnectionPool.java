package com.shirey.cafe.db;

import com.shirey.cafe.exception.ConnectionException;
import com.shirey.cafe.manager.DatabaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock instanceLock = new ReentrantLock(true);
    private static ReentrantLock poolLock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private final int POOL_SIZE;
    private final int CONNECTION_TIMEOUT;
    private Condition isFree = poolLock.newCondition();
    private ArrayDeque<ProxyConnection> connectionPool;
    private AtomicInteger connectionsCreatedCount;

    private ConnectionPool() {
        POOL_SIZE = initPoolSize();
        CONNECTION_TIMEOUT = initTimeout();
        connectionPool = new ArrayDeque<>();
        connectionsCreatedCount = new AtomicInteger(0);
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            instanceLock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() throws ConnectionException {

        poolLock.lock();
        try {
            while (connectionPool.isEmpty()) {
                if (connectionsCreatedCount.get() != POOL_SIZE) {
                    Connection connection = ConnectionCreator.createConnection();
                    connectionsCreatedCount.getAndIncrement();
                    LOGGER.log(Level.TRACE, "connection was acquired from connection pool.");
                    return new ProxyConnection(connection);
                }
                isFree.await(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                if (connectionPool.isEmpty()) {
                    throw new ConnectionException("connection timeout exceeded... connection isn't available now, try later.");
                }
            }
            LOGGER.log(Level.TRACE, "connection was acquired from connection pool.");
            return connectionPool.poll();
        } catch (InterruptedException e) {
            throw new ConnectionException("current thread was interrupted, can't get connection.", e);
        } finally {
            poolLock.unlock();
        }
    }

    void releaseConnection(ProxyConnection connection) {

        poolLock.lock();
        try {
            connectionPool.offer(connection);
            isFree.signal();
            LOGGER.log(Level.TRACE, "connection was released to connection pool.");
        } finally {
            poolLock.unlock();
        }
    }

    public void closeConnections() {

        for (int i = 0; i < connectionsCreatedCount.get(); i++) {
            poolLock.lock();
            try {
                while (connectionPool.isEmpty()) {
                    isFree.await();
                }
                ProxyConnection proxyConnection = connectionPool.poll();
                proxyConnection.closeConnection();
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "current thread was interrupted, can't close connection.", e);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "problems with closing connection, connection wasn't closed.", e);
            } finally {
                poolLock.unlock();
            }
        }
        if (connectionsCreatedCount.get() != 0) {
            LOGGER.log(Level.TRACE, "all connections to database were closed (check error log if errors were acquired).");
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "exception while deregistering driver, driver wasn't deregistered", e);
            }
        }
        LOGGER.log(Level.TRACE, "all SQL drivers were deregistered (check error log if errors were acquired).");
    }

    private int initPoolSize() {
        int value;
        try {
            value = Integer.parseInt(DatabaseManager.getProperty("db.poolSize"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "pool size isn't a number, check database resources.configuration file.", e);
            throw new RuntimeException("pool size isn't a number, check database resources.configuration file.", e);
        }
        if (value <= 0) {
            LOGGER.log(Level.ERROR, "pool size can't be less or equals zero, check database resources.configuration file.");
            throw new RuntimeException("pool size can't be less or equals zero, check database resources.configuration file.");
        }
        return value;
    }

    private int initTimeout() {
        int value;
        try {
            value = Integer.parseInt(DatabaseManager.getProperty("db.connectionTimeout"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "connection timeout isn't a number, check database resources.configuration file.", e);
            throw new RuntimeException("connection timeout isn't a number, check database resources.configuration file.", e);
        }
        if (value <= 0) {
            LOGGER.log(Level.ERROR, "timeout can't be less or equals zero, check database resources.configuration file.");
            throw new RuntimeException("timeout can't be less or equals zero, check database resources.configuration file.");
        }
        return value;
    }

}
