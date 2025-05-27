package com.salecoursecms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {
    private Long userId;
    private String title;
    private String content;
    private Long createBy; // id người tạo thông báo này
    private String type;// COURSE_CREATED, COURSE_APPROVED, etc
    private Long redirectId;
    private int status; // 0 chưa đọc, 1 đọc rồi
}
