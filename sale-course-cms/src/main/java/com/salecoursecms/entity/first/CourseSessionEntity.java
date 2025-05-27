package com.salecoursecms.entity.first;

import com.salecoursecms.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = TableConst.COURSE_SESSION)
@NoArgsConstructor
@AllArgsConstructor
public class CourseSessionEntity {

    @SequenceGenerator(name = TableConst.COURSE_SESSION_SEQ, sequenceName = TableConst.COURSE_SESSION_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.COURSE_SESSION_SEQ)
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "COURSE_ID")
    private Long courseId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "SESSION_DATE")
    private Date sessionDate;
    @Column(name = "SESSION_TYPE")
    private String sessionType; // ONLINE, OFFLINE
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "STATUS")
    private int status;
}
