package com.salecoursecms.service.impl;

import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.dto.reponse.CourseSessionReponse;
import com.salecoursecms.entity.first.CourseEntity;
import com.salecoursecms.entity.first.CourseSessionEntity;
import com.salecoursecms.exception.SystemErrorException;
import com.salecoursecms.mapper.CourseSessionMapper;
import com.salecoursecms.repository.first.CourseSessionRepository;
import com.salecoursecms.service.CourseSessionService;
import com.salecoursecms.utils.CourseScheduleUtils;
import com.salecoursecms.utils.LessonPeriodHardcodeUtils;
import com.salecoursecms.utils.VietnamHolidayUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseSessionServiceImpl implements CourseSessionService {
    private final CourseSessionMapper courseSessionMapper;
    private final CourseSessionRepository courseSessionRepository;

    @Override
    public List<CourseSessionReponse> generatorCourseSession(CourseEntity courseEntity) {
        try {
            // Lấy ngày bắt đầu của khóa học (kiểu java.util.Date) và chuyển sang java.time.LocalDate để dễ xử lý thời gian
            LocalDate current = courseEntity.getStartDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Khởi tạo danh sách để chứa các buổi học sẽ được tạo
            List<CourseSessionEntity> courseSessionList = new ArrayList<>();

            // Xác định loại buổi học: "ONLINE" nếu sessionType == 1, ngược lại là "OFFLINE"
            String sessionType = courseEntity.getSessionType() == 1 ? "ONLINE" : "OFFLINE";

            // Tách chuỗi các ngày học trong tuần (ví dụ: "mon,wed,fri") thành danh sách các chuỗi ["mon", "wed", "fri"]
            List<String> days = CourseScheduleUtils.splitDays(courseEntity.getDaysOfWeek());

            // Chuyển danh sách chuỗi ngày thành danh sách DayOfWeek (enum của Java: MONDAY, TUESDAY, ...)
            List<DayOfWeek> studyDays = days.stream()
                    .map(day -> DayOfWeek.valueOf(day.toUpperCase()))
                    .collect(Collectors.toList());

            // Lấy danh sách các ngày nghỉ lễ ở Việt Nam theo năm bắt đầu của khóa học
            Set<LocalDate> holidays = VietnamHolidayUtils.getVietnamHolidays(current.getYear());

            int sessionCount = 0; // Đếm số buổi đã tạo
            while (sessionCount < courseEntity.getNumberOfSession()) {
                // Nếu ngày hiện tại không phải là ngày học hoặc là ngày nghỉ lễ thì bỏ qua
                if (!studyDays.contains(current.getDayOfWeek()) || holidays.contains(current)) {
                    current = current.plusDays(1); // chuyển sang ngày tiếp theo
                    continue;
                }

                // Nếu đang chuyển sang năm mới thì cập nhật lại danh sách ngày nghỉ lễ
                if (current.getMonthValue() == 1 && current.getDayOfMonth() == 1) {
                    holidays = VietnamHolidayUtils.getVietnamHolidays(current.getYear());
                }

                // Lấy chỉ số tiết học từ entity (VD: 0 = tiết 1, 1 = tiết 2, ...)
                int lessonIndex = courseEntity.getStartTimeMinute(); // startTimeMinute giờ đóng vai trò là chỉ số tiết học
                int startMinute = LessonPeriodHardcodeUtils.getStartTimeByIndex(lessonIndex);

                // Tính thời gian bắt đầu buổi học
                LocalDateTime sessionDateTime = current.atStartOfDay().plusMinutes(startMinute);
                Date sessionDateWithTime = Date.from(sessionDateTime.atZone(ZoneId.systemDefault()).toInstant());


                CourseSessionEntity session = new CourseSessionEntity();
                session.setSessionDate(sessionDateWithTime);
                session.setCourseId(courseEntity.getId());
                session.setCreateDate(new Date());
                session.setStatus(1);

                session.setTitle("Session " + (sessionCount + 1));
                session.setDescription("");
                session.setLocation("");
                session.setSessionType(sessionType);

                courseSessionList.add(session);
                sessionCount++;

                current = current.plusDays(1);  // Chuyển sang ngày tiếp theo
            }

            // Lưu toàn bộ danh sách buổi học vào database
            courseSessionRepository.saveAll(courseSessionList);

            // Chuyển danh sách entity sang DTO (response object) để trả về
            List<CourseSessionReponse> reponseList = courseSessionMapper.toReponseList(courseSessionList);
            return reponseList;

        } catch (Exception e) {
            // Ghi log lỗi và ném exception lỗi hệ thống
            log.error("[ERROR] " + e.getMessage());
            throw new SystemErrorException(MessageConst.INTERNAL_SERVER_ERROR);
        }
    }

}
