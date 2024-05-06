package com.clearsolutions.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;

class TimeUtilsTest {

    private static final String FORMAT = "DD-MM-YYYY HH:mm:ss";
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    @SneakyThrows
    @Test
    void formatter() {
        //given
        var dateFormat = "1-01-2024 00:00:00";
        var expected = "01-01-2024 00:00:00";
        var date = pars(dateFormat);

        //when
        var actual = TimeUtils.formatter(date);

        //then
        Assertions.assertEquals(expected, actual);

    }

    @SneakyThrows
    @Test
    void toLocalDate() {
        //given
        var dateFormat = "1-01-2024 00:00:00";
        var expected = LocalDate.of(2024, 1, 1);
        var date = pars(dateFormat);

        //when
        var actual = TimeUtils.toLocalDate(date);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @SneakyThrows
    private Date pars(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        dateFormat.setTimeZone(UTC);
        return dateFormat.parse(date);
    }

}