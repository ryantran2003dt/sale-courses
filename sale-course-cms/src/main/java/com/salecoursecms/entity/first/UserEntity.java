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
@NoArgsConstructor
@Entity
@Table(name = TableConst.USER)
@AllArgsConstructor
public class UserEntity {
    @Id
    @SequenceGenerator(name = TableConst.USER_SEQ, sequenceName = TableConst.USER_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.USER_SEQ)
    @Column(name = "ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "GENDER")
    private int gender;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "IMG")
    private String img;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "ROLE_ID")
    private Long roldId;
}
