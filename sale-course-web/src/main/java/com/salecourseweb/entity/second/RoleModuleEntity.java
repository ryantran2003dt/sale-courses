package com.salecourseweb.entity.second;

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
@Table(name = TableConst.ROLE_MODULE)
public class RoleModuleEntity {
    @Id
    @SequenceGenerator(name = TableConst.ROLE_MODULE_SEQ, sequenceName = TableConst.ROLE_MODULE_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.ROLE_MODULE_SEQ)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "MODULE_ID")
    private Long moduleId;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "STATUS")
    private int status;
}
