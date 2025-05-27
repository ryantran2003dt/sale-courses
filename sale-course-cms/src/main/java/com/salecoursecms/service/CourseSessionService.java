package com.salecoursecms.service;

import com.salecoursecms.dto.reponse.CourseSessionReponse;
import com.salecoursecms.entity.first.CourseEntity;
import com.salecoursecms.entity.first.CourseSessionEntity;

import java.util.List;

public interface CourseSessionService {
    List<CourseSessionReponse> generatorCourseSession(CourseEntity courseEntity);
}
