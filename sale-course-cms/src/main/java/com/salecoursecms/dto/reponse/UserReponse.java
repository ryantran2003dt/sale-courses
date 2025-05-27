package com.salecoursecms.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReponse {
    private Long id;

    private String username;

    private String fullName;

    private Integer gender;

    private String phone;

    private Date dateOfBirth;

    private String email;

    private String img;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    private Long roldId;
}
