package com.salecoursecms.dto.reponse;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseSessionReponse {

    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private Date sessionDate;
    private String sessionType; // ONLINE, OFFLINE
    private String location;
}
