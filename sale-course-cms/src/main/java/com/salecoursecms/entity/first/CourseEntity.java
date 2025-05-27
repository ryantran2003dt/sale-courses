package com.salecoursecms.entity.first;

import com.salecoursecms.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Table(name = TableConst.COURSE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

    @SequenceGenerator(name = TableConst.COURSE_SEQ, sequenceName = TableConst.COURSE_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.COURSE_SEQ)
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME_COURSE")
    private String nameCourse;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PRICE")
    private float price;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "NUMBER_OF_SESSION")
    private int numberOfSession;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "CREATE_BY")
    private Long createBy;
    @Column(name = "UPDATE_BY")
    private Long updateBy;
    @Column(name = "TEACHER_ID")
    private Long teacherId;
    @Column(name = "STATUS")
    private int status; // 0 init, 1 approve, 2 inject, 3 delete
    @Column(name = "DAYS_OF_WEEK")
    private String daysOfWeek;
    @Column(name = "SESSION_TYPE")
    private int sessionType; //1 online, 2 offline
    @Column(name = "START_TIME_MINUTE")
    private int startTimeMinute; // Số phút tính từ 00:00

}
