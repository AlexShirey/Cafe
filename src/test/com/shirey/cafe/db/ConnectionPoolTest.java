package com.shirey.cafe.db;

import com.shirey.cafe.exception.ConnectionException;
import com.shirey.cafe.manager.DatabaseManager;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayDeque;


public class ConnectionPoolTest {

    private static int poolSize;

    @BeforeClass
    private static void init() {
        poolSize = Integer.parseInt(DatabaseManager.getProperty("db.poolSize"));
    }

    @Test
    public void testGetInstance() {

        Assert.assertNotNull(ConnectionPool.getInstance());
    }

    @Test
    public void testGetConnection() throws ConnectionException {

        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        Assert.assertNotNull(connection);
        ConnectionPool.getInstance().releaseConnection(connection);
    }


    @Test(expectedExceptions = ConnectionException.class, expectedExceptionsMessageRegExp = "connection timeout exceeded... connection isn't available now, try later.")
    public void testConnectionTimeOutException() throws ConnectionException {

        ArrayDeque<ProxyConnection> createdConnections = new ArrayDeque<>();
        try {
            for (int i = 0; i < poolSize + 1; i++) {
                createdConnections.offer(ConnectionPool.getInstance().getConnection());
            }
        } finally {
            while (!createdConnections.isEmpty()) {
                ConnectionPool.getInstance().releaseConnection(createdConnections.poll());
            }
        }
    }


    @AfterTest
    public void releaseAndCloseConnection() throws Exception {

        ConnectionPool.getInstance().closeConnections();
    }

}