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
    @Column(name = "ID", nullable = false, length = 100)
    private Long id;

    @Column(name = "FULLNAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "GENDER", length = 10)
    private int gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB")
    private Date dob;

    @Column(name = "ADDRESS", length = 255)
    private String address;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "PASSWORD", length = 255)
    private String password;

    @Column(name = "AVATAR_URL", length = 255)
    private String avatarUrl;

    @Column(name = "GUARDIAN_NAME", length = 100)
    private String guardianName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "STATUS", nullable = false)
    private int status;
    @Column(name ="EMAIL_VERIFIED",nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "PROVIDER_ID", nullable = false)
    private Long providerId;

    public StudentEntity(String firstName, String lastName, String email, String pictureUrl) {
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.avatarUrl = pictureUrl;
    }
}
