package com.shirey.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalDateTimeParser {

    private static final String LOCALE_DATE_TIME_MINUTES_PATTERN = "yyyy-MM-dd'T'HH:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LOCALE_DATE_TIME_MINUTES_PATTERN);

    private LocalDateTimeParser() {
    }


    public static Date parse(String ltdDate) throws ParseException {

        return simpleDateFormat.parse(ltdDate);

    }

}
