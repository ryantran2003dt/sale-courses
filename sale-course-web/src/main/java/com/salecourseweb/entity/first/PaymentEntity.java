package com.salecourseweb.entity.first;

import com.salecourseweb.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableConst.PAYMENT)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "PAYMET_NAME", length = 255)
    private String paymentName;
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
    @Column(name = "STATUS", nullable = false)
    private int status;
}
