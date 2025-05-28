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
@Table(name = TableConst.MODULE)
public class ModuleEntity {
    @Id
    @SequenceGenerator(name = TableConst.MODULE_SEQ, sequenceName = TableConst.MODULE_SEQ, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TableConst.MODULE_SEQ)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "MODULE_NAME")
    private String moduleName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CREATE_DATE")
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    @Column(name = "STATUS")
    private int status;
    @Column(name = "LINK")
    private String link;
}
