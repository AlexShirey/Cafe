package com.shirey.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * The {@code LocalDateTimeParser} class contains method that
 * parses a {@code String} value of {@code LocalDateTime} class object
 * (such as {@code 2007-12-03T10:15})
 * to a {@code Date}.
 *
 * @author Alex Shirey
 * @see java.time.LocalDateTime#toString()
 */

public class LocalDateTimeParser {

    /**
     * Pattern for ISO-8601 formats, such as {@code 2007-12-03T10:15}.
     */
    private static final String LOCALE_DATE_TIME_MINUTES_PATTERN = "yyyy-MM-dd'T'HH:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LOCALE_DATE_TIME_MINUTES_PATTERN);

    /**
     * Don't let anyone instantiate this class.
     */
    private LocalDateTimeParser() {
    }

    /**
     * Parses text from the beginning of the given string to produce a date.
     *
     * @param localeDateTime A <code>String</code> value that should be parsed.
     * @return A {@code Date} parsed from the string.
     * @throws ParseException if the value cannot be parsed.
     * @see java.text.SimpleDateFormat#parse(String)
     */
    public static Date parseLdtStringToDate(String localeDateTime) throws ParseException {

        return simpleDateFormat.parse(localeDateTime);
    }

}