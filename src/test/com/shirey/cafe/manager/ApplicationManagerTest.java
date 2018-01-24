package com.shirey.cafe.manager;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplicationManagerTest {

    @Test
    public void testGetProperty() {

        String actual = ApplicationManager.getProperty("app.orderNotEarlierTimeInMinutes");
        String expected = "30";

        Assert.assertEquals(actual, expected);
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void testGetPropertyException() {

        ApplicationManager.getProperty("app.noSuchKey");
    }

}