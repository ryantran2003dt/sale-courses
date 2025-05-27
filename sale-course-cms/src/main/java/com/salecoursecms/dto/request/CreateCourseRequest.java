package com.salecoursecms.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class CreateCourseRequest {
    private String nameCourse;
    private String description;
    private float price;
    private String image;
    private Date startDate;
    private int numberOfSession;
    private Long createBy;
    private Long teacherId;
    private int sessionType;
    private int status = 0; // 0 init, 1 approve, 2 inject, 3 delete
    private int startTimeMinute;
    private List<String> daysOfWeek;
}
