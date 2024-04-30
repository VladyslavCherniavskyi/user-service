package com.clearsolutions.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtils {

    private static final String FORMAT = "DD-MM-YYYY HH:mm:ss";
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static String formatter(Date date) {
        var dateFormat = new SimpleDateFormat(FORMAT);
        dateFormat.setTimeZone(UTC);
        return dateFormat.format(date);
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(UTC.toZoneId()).toLocalDate();
    }

}
