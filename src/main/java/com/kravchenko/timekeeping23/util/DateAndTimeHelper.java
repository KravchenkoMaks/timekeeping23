package com.kravchenko.timekeeping23.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DateAndTimeHelper {

    public static LocalDate createDate(String date) {

        return LocalDate.parse(date);
    }

    public static LocalDate doneDate(LocalDate create, String done) {

        return "".equals(done.trim()) ? create : LocalDate.parse(done);
    }

    public static Integer effort(String effort) {
        return "".equals(effort.trim()) ? 0 : Integer.parseInt(effort);
    }
}
