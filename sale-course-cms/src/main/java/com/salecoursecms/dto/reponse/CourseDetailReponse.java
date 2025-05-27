package com.salecoursecms.dto.reponse;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CourseDetailReponse {
    private Long id;
    private String nameCourse;
    private String description;
    private float price;
    private String image;
    private Date startDate;
    private int numberOfSession;
    private Date createDate;
    private Long createBy;
    private Long teacherId;
    List<CourseSessionReponse> sessions;
}
