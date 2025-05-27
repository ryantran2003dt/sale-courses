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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableConst.TRANSACTION)
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId;

    @Column(name = "PAYMENT_ID", nullable = false)
    private Long paymentId;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    @Column(name = "TRANSACTIO_STATUS", nullable = false)
    private int transactionStatus; // 1 "PENDING", 2 "COMPLETED", 3 "FAILED"

    @Column(name = "TRANSACTION_CODE", unique = true, nullable = false, length = 100)
    private String transactionCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private Date transactionDate;

    @Column(name = "PAYMENT_METHOD", length = 50)
    private String paymentMethod;

    @Column(name = "REPONSE_MESSAGE", length = 255)
    private String responseMessage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
}
