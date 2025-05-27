package com.salecoursecms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private Long id;
    private String password;
    private String fullName;
    private int gender;
    private String phone;
    private Date dateOfBirth;
    private String email;
    private String img;
    private Date updateDate;
    private Long roldId;
}
