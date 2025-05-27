package com.salecoursecms.service;

import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.request.CreateCourseRequest;
import com.salecoursecms.dto.request.PagingRequest;
import com.salecoursecms.dto.request.UpdateCourseRequest;
import com.salecoursecms.dto.request.UpdateStatusRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface CourseService {
    BaseReponse<?> createCourse(CreateCourseRequest req, HttpServletRequest httpServletRequest);
    BaseReponse<?> findAllCourse(String search, PagingRequest pagingRequest);
    BaseReponse<?> updateStatusCourse(UpdateStatusRequest req);
    BaseReponse<?> updateCourse(UpdateCourseRequest req, HttpServletRequest httpServletRequest);
    BaseReponse<?> findCourseById(Long id);
}
