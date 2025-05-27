package com.salecoursecms.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateCourseRequest {
    private Long id;
    private String nameCourse;
    private String description;
    private float price;
    private String image;
    private int numberOfSession;
    private Date startDate;
    private Date updateDate;
    private Long updateBy;
    private Long teacherId;
    private int status = 0; // 0 init, 1 approve, 2 inject, 3 delete
    private int sessionType; //1 online, 2 offline
    private int startTimeMinute;
    private List<String> daysOfWeek;
}
