package com.example.creamsoda.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static LocalDateTime parseLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDate parseDateString(String dateString) {
        LocalDateTime dateTime = LocalDateTime.parse(dateString, FORMATTER);
        return dateTime.toLocalDate();
    }

    public static LocalTime parseLocalTime(String timeString, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(timeString, formatter);
    }

    public static LocalDate fromLocalDateTime(LocalDateTime dateTime) {
        return LocalDate.from(dateTime);
    }
}