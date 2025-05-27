package com.salecoursecms.entity.first;

import com.salecoursecms.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableConst.NOTIFICATION)
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.NOTIFICATION_SEQ)
    @SequenceGenerator(name = TableConst.NOTIFICATION_SEQ, sequenceName = TableConst.NOTIFICATION_SEQ, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS") // đã đọc chưa
    private int status; // 0 chưa đọc, 1 đọc rồi

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CREATE_BY")
    private Long createBy; // id người tạo thông báo này

    @Column(name = "TYPE") // COURSE_CREATED, COURSE_APPROVED, etc.
    private String type;

    @Column(name = "REDIRECT_ID") // ID để redirect khi click vào
    private Long redirectId;
}

