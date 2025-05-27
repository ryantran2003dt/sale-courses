package com.salecoursecms.mapper;

import com.salecoursecms.dto.reponse.CourseSessionReponse;
import com.salecoursecms.entity.first.CourseSessionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseSessionMapper {
    public List<CourseSessionReponse> toReponseList(List<CourseSessionEntity> courseSessionEntityList) {
        List<CourseSessionReponse> courseSessionReponseList = new ArrayList<>();
        for (CourseSessionEntity courseSessionEntity : courseSessionEntityList) {
            CourseSessionReponse courseSessionReponse = new CourseSessionReponse();
            courseSessionReponse.setId(courseSessionEntity.getId());
            courseSessionReponse.setCourseId(courseSessionEntity.getCourseId());
            courseSessionReponse.setTitle(courseSessionEntity.getTitle());
            courseSessionReponse.setSessionDate(courseSessionEntity.getSessionDate());
            courseSessionReponse.setDescription(courseSessionEntity.getDescription());
            courseSessionReponse.setSessionType(courseSessionEntity.getSessionType());
            courseSessionReponse.setLocation(courseSessionEntity.getLocation());
            courseSessionReponseList.add(courseSessionReponse);
        }
        return courseSessionReponseList;
    }
}
