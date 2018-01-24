package com.shirey.cafe.manager;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PageManagerTest {

    @Test
    public void testGetProperty() {

        String actual = PageManager.getProperty("page.index");
        String expected = "/index.jsp";

        Assert.assertEquals(actual, expected);
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void testGetPropertyException() {

        PageManager.getProperty("page.indexx");
    }

}