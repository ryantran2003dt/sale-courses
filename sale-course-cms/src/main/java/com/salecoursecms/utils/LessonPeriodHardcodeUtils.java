package com.salecoursecms.utils;

import java.util.List;
import java.util.Optional;

public class LessonPeriodHardcodeUtils {
    public static final List<LessonPeriod> LESSON_PERIODS = List.of(
            new LessonPeriod("Tiết 1", 8 * 60),       // 08:00
            new LessonPeriod("Tiết 2", 9 * 60 + 30),  // 09:30
            new LessonPeriod("Tiết 3", 11 * 60),      // 11:00
            new LessonPeriod("Tiết 4", 13 * 60 + 30), // 13:30
            new LessonPeriod("Tiết 5", 15 * 60),      // 15:00
            new LessonPeriod("Tiết 6", 16 * 60 + 30), // 16:30
            new LessonPeriod("Tiết 7", 18 * 60),      // 18:00
            new LessonPeriod("Tiết 8", 19 * 60 + 30), // 19:30
            new LessonPeriod("Tiết 9", 21 * 60)       // 21:00
    );

    public static class LessonPeriod {
        private final String name;
        private final Integer startTimeMinute;

        public LessonPeriod(String name, Integer startTimeMinute) {
            this.name = name;
            this.startTimeMinute = startTimeMinute;
        }

        public String getName() {
            return name;
        }

        public Integer getStartTimeMinute() {
            return startTimeMinute;
        }
    }

    // Tiện ích: lấy thời gian bắt đầu (minute) theo chỉ số
    public static Integer getStartTimeByIndex(int index) {
        if (index < 0 || index >= LESSON_PERIODS.size()) {
            throw new IllegalArgumentException("Chỉ số tiết học không hợp lệ: " + index);
        }
        return LESSON_PERIODS.get(index).getStartTimeMinute();
    }
}
