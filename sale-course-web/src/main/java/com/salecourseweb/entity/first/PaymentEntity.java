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
    @Column(name = "ID")
    private Long id;
    @Column(name = "PAYMET_NAME")
    private String paymentName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMG_URL")
    private String imgUrl;
    @Column(name = "STATUS")
    private int status;
}
