package com.salecoursecms.controller;

import com.salecoursecms.constant.UrlConst;
import com.salecoursecms.dto.request.CreateCourseRequest;
import com.salecoursecms.dto.request.PagingRequest;
import com.salecoursecms.dto.request.UpdateCourseRequest;
import com.salecoursecms.dto.request.UpdateStatusRequest;
import com.salecoursecms.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UrlConst.COURSE)
public class CourseController {
    private final CourseService courseService;

    @GetMapping(UrlConst.GET_LIST)
    public ResponseEntity<?> getAllUser(String search, PagingRequest pagingRequest) {
        return ResponseEntity.ok(courseService.findAllCourse(search, pagingRequest));
    }

    @PostMapping(UrlConst.CREATE)
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseRequest req, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(courseService.createCourse(req, httpServletRequest));
    }
    @PostMapping(UrlConst.UPDATE_STATUS)
    public ResponseEntity<?> updateStatusCourse(@RequestBody UpdateStatusRequest req) {
        return ResponseEntity.ok(courseService.updateStatusCourse(req));
    }
    @PostMapping(UrlConst.UPDATE)
    public ResponseEntity<?> updateCourse(@RequestBody UpdateCourseRequest req, HttpServletRequest httpServletRequest) {
        log.info("request: "+req);
        return ResponseEntity.ok(courseService.updateCourse(req,httpServletRequest));
    }
    @PostMapping(UrlConst.GET_BY_ID)
    public ResponseEntity<?> getUserById(@RequestBody UpdateStatusRequest request){
        log.info("+++++++++++++++"+request.getId());
        return ResponseEntity.ok(courseService.findCourseById(request.getId()));
    }
}
