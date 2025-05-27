package com.salecoursecms.utils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class VietnamHolidayUtils {
    public static Set<LocalDate> getVietnamHolidays(int year) {
        Set<LocalDate> holidays = new HashSet<>();

        holidays.add(LocalDate.of(year, 1, 1));   // Tết Dương
        holidays.add(LocalDate.of(year, 4, 30));  // Giải phóng miền Nam
        holidays.add(LocalDate.of(year, 5, 1));   // Quốc tế lao động
        holidays.add(LocalDate.of(year, 9, 2));   // Quốc khánh

        // Giỗ Tổ Hùng Vương (10/3 âm lịch), hard-code cho 2026 - 2065
        switch (year) {
            case 2026 -> holidays.add(LocalDate.of(2026, 3, 28));
            case 2027 -> holidays.add(LocalDate.of(2027, 4, 17));
            case 2028 -> holidays.add(LocalDate.of(2028, 4, 5));
            case 2029 -> holidays.add(LocalDate.of(2029, 3, 25));
            case 2030 -> holidays.add(LocalDate.of(2030, 4, 13));
            case 2031 -> holidays.add(LocalDate.of(2031, 4, 3));
            case 2032 -> holidays.add(LocalDate.of(2032, 3, 22));
            case 2033 -> holidays.add(LocalDate.of(2033, 4, 10));
            case 2034 -> holidays.add(LocalDate.of(2034, 3, 30));
            case 2035 -> holidays.add(LocalDate.of(2035, 4, 18));
            case 2036 -> holidays.add(LocalDate.of(2036, 4, 6));
            case 2037 -> holidays.add(LocalDate.of(2037, 3, 26));
            case 2038 -> holidays.add(LocalDate.of(2038, 4, 14));
            case 2039 -> holidays.add(LocalDate.of(2039, 4, 3));
            case 2040 -> holidays.add(LocalDate.of(2040, 3, 22));
            case 2041 -> holidays.add(LocalDate.of(2041, 4, 10));
            case 2042 -> holidays.add(LocalDate.of(2042, 3, 30));
            case 2043 -> holidays.add(LocalDate.of(2043, 4, 18));
            case 2044 -> holidays.add(LocalDate.of(2044, 4, 6));
            case 2045 -> holidays.add(LocalDate.of(2045, 3, 26));
            case 2046 -> holidays.add(LocalDate.of(2046, 4, 14));
            case 2047 -> holidays.add(LocalDate.of(2047, 4, 4));
            case 2048 -> holidays.add(LocalDate.of(2048, 3, 23));
            case 2049 -> holidays.add(LocalDate.of(2049, 4, 11));
            case 2050 -> holidays.add(LocalDate.of(2050, 3, 31));
            case 2051 -> holidays.add(LocalDate.of(2051, 4, 19));
            case 2052 -> holidays.add(LocalDate.of(2052, 4, 7));
            case 2053 -> holidays.add(LocalDate.of(2053, 3, 27));
            case 2054 -> holidays.add(LocalDate.of(2054, 4, 15));
            case 2055 -> holidays.add(LocalDate.of(2055, 4, 4));
            case 2056 -> holidays.add(LocalDate.of(2056, 3, 24));
            case 2057 -> holidays.add(LocalDate.of(2057, 4, 12));
            case 2058 -> holidays.add(LocalDate.of(2058, 4, 1));
            case 2059 -> holidays.add(LocalDate.of(2059, 4, 20));
            case 2060 -> holidays.add(LocalDate.of(2060, 4, 8));
            case 2061 -> holidays.add(LocalDate.of(2061, 3, 28));
            case 2062 -> holidays.add(LocalDate.of(2062, 4, 16));
            case 2063 -> holidays.add(LocalDate.of(2063, 4, 5));
            case 2064 -> holidays.add(LocalDate.of(2064, 3, 24));
            case 2065 -> holidays.add(LocalDate.of(2065, 4, 13));
        }

        return holidays;
    }
}
