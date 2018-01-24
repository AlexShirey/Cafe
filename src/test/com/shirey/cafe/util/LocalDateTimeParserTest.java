package com.shirey.cafe.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class LocalDateTimeParserTest {

    private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @Test
    public void testParse() throws Exception {

        int year = 2018;
        int month = 1;
        int day = 23;
        int hour = 14;
        int minute = 1;

        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);
        Date dateExpected = new SimpleDateFormat(DATE_PATTERN).parse("2018-01-23 14:01");

        Assert.assertEquals(LocalDateTimeParser.parseLdtStringToDate(localDateTime.toString()), dateExpected);
    }

}