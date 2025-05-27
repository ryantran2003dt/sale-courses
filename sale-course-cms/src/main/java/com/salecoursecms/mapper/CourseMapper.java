package com.salecoursecms.mapper;

import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.dto.reponse.CourseDetailReponse;
import com.salecoursecms.dto.reponse.CourseReponse;
import com.salecoursecms.dto.reponse.CourseSessionReponse;
import com.salecoursecms.dto.request.CreateCourseRequest;
import com.salecoursecms.dto.request.UpdateCourseRequest;
import com.salecoursecms.entity.first.CourseEntity;
import com.salecoursecms.exception.ResourceNotFoundException;
import com.salecoursecms.exception.SystemErrorException;
import com.salecoursecms.repository.first.CourseRepository;
import com.salecoursecms.utils.CourseScheduleUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CourseMapper {
    private final CourseRepository courseRepository;
    public CourseEntity toCreateCourse(CreateCourseRequest req) {
        CourseEntity course = new CourseEntity();
        course.setNameCourse(req.getNameCourse());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        course.setImage(req.getImage());
        course.setStartDate(req.getStartDate());
        course.setNumberOfSession(req.getNumberOfSession());
        course.setCreateDate(new Date());
        course.setCreateBy(req.getCreateBy());
        course.setTeacherId(req.getTeacherId());
        course.setStatus(req.getStatus());
        course.setSessionType(req.getSessionType());
        course.setStartTimeMinute(req.getStartTimeMinute());
        course.setDaysOfWeek(CourseScheduleUtils.joinDays(req.getDaysOfWeek()));
        return course;
    }
    public CourseReponse toReponse(CourseEntity course) {
        CourseReponse courseReponse = new CourseReponse();
        courseReponse.setId(course.getId());
        courseReponse.setNameCourse(course.getNameCourse());
        courseReponse.setDescription(course.getDescription());
        courseReponse.setPrice(course.getPrice());
        courseReponse.setImage(course.getImage());
        courseReponse.setStartDate(course.getStartDate());
        courseReponse.setNumberOfSession(course.getNumberOfSession());
        courseReponse.setCreateBy(course.getCreateBy());
        courseReponse.setTeacherId(course.getTeacherId());
        courseReponse.setStatus(course.getStatus());
        return courseReponse;
    }
    public CourseDetailReponse toDetailReponse(CourseEntity course, List<CourseSessionReponse> courseSessionReponses) {
        CourseDetailReponse courseDetailReponse = new CourseDetailReponse();
        courseDetailReponse.setId(course.getId());
        courseDetailReponse.setNameCourse(course.getNameCourse());
        courseDetailReponse.setDescription(course.getDescription());
        courseDetailReponse.setPrice(course.getPrice());
        courseDetailReponse.setImage(course.getImage());
        courseDetailReponse.setStartDate(course.getStartDate());
        courseDetailReponse.setNumberOfSession(course.getNumberOfSession());
        courseDetailReponse.setCreateBy(course.getCreateBy());
        courseDetailReponse.setTeacherId(course.getTeacherId());
        courseDetailReponse.setSessions(courseSessionReponses);
        return courseDetailReponse;
    }
    public CourseEntity toUpdateCourse(UpdateCourseRequest req) {
        CourseEntity course = courseRepository.findById(req.getId()).orElse(null);
        log.info("numberOfSession: "+req.getNumberOfSession());
        Date currentDate = new Date();
        if (course == null){
            throw new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND);
        }if (course.getStartDate().before(currentDate)) {
            throw new SystemErrorException(MessageConst.TIME_EXPIRED);
        }
        course.setNameCourse(req.getNameCourse());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        course.setImage(req.getImage());
        course.setStartDate(req.getStartDate());
        course.setNumberOfSession(req.getNumberOfSession());
        course.setUpdateDate(new Date());
        course.setStatus(req.getStatus());
        course.setTeacherId(req.getTeacherId());
        course.setSessionType(req.getSessionType());
        course.setStartTimeMinute(req.getStartTimeMinute());
        course.setDaysOfWeek(CourseScheduleUtils.joinDays(req.getDaysOfWeek()));
        courseRepository.save(course);
        return course;
    }
}
