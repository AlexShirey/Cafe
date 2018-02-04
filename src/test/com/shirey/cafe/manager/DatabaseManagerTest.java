package com.shirey.cafe.manager;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseManagerTest {

    @Test
    public void testGetProperty() {

        String actual = DatabaseManager.getProperty("db.poolSize");
        String expected = "10";

        Assert.assertEquals(actual, expected);
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void testGetPropertyException() {

        DatabaseManager.getProperty("db.pooolSize");
    }

}