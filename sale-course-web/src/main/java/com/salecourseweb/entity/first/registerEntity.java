package com.salecourseweb.entity.first;

import com.salecourseweb.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableConst.REGISTER)
public class registerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId; // FK đến StudentEntity

    @Column(name = "COURSE_ID", nullable = false)
    private Long courseId; // FK đến CourseEntity (nếu có)

    @Column(name = "STATUS", length = 20, nullable = false)
    private int status; // REGISTERED, CANCELLED, COMPLETED

    @Column(name = "NOTE", length = 255)
    private String note;

    @Column(name = "IS_PAID")
    private Boolean isPaid;

    @Column(name = "TRANSACTION_ID")
    private Long transactionId; // FK đến TransactionEntity

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

}
