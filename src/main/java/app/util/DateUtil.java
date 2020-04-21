package main.java.app.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final YearMonth NOW = YearMonth.of(3000, 1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(YearMonth date) {
        if (date == null) return "";
        return date.equals(NOW) ? "н.в." : date.format(DATE_FORMATTER);
    }
}
