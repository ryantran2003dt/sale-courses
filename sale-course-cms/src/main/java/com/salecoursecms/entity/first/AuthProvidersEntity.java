package com.salecoursecms.entity.first;

import com.salecoursecms.constant.TableConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = TableConst.AUTHPROVIDERS)
@NoArgsConstructor
@AllArgsConstructor
public class AuthProvidersEntity {

    @Id
    @SequenceGenerator(name = TableConst.AUTHPROVIDERS_SEQ, sequenceName = TableConst.AUTHPROVIDERS_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.AUTHPROVIDERS_SEQ)
    @Column(name = "ID")
    private Long id;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "PROVIDER")
    private String provider; //ex: localhost, google, facebook
    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;
    @Column(name = "PASSWORD_HASH")
    private String passwordHash;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
}
