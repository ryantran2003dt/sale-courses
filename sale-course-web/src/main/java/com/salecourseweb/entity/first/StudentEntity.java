package com.salecourseweb.entity.first;

import com.salecourseweb.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableConst.STUDENT)
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "GENDER")
    private int gender;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AVATAR_URL")
    private String avatarUrl;

    @Column(name = "GUARDIAN_NAME")
    private String guardianName;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "STATUS")
    private int status;
    @Column(name ="EMAIL_VERIFIED")
    private Boolean emailVerified = false;

    @Column(name = "PROVIDER_ID")
    private Long providerId;

    public StudentEntity(String firstName, String lastName, String email, String pictureUrl) {
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.avatarUrl = pictureUrl;
    }
}
