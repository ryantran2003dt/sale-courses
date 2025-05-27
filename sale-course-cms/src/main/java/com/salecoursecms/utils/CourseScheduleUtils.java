package com.salecoursecms.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CourseScheduleUtils {

    public static String joinDays(List<String> days) {
        if (days == null || days.isEmpty()) {
            return "";
        }

        return days.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(day -> day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase())
                .collect(Collectors.joining("_"));
    }

    /**
     * Tách chuỗi "Monday_Tuesday" thành List<String> các ngày.
     */
    public static List<String> splitDays(String dayString) {
        if (dayString == null || dayString.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(dayString.split("_"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
