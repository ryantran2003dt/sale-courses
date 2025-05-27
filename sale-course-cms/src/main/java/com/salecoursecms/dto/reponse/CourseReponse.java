package com.salecoursecms.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseReponse {
    private Long id;
    private String nameCourse;
    private String description;
    private float price;
    private String image;
    private Date startDate;
    private int numberOfSession;
    private Long createBy;
    private Long teacherId;
    private int status = 0; // 0 init, 1 approve, 2 inject, 3 delete
}
