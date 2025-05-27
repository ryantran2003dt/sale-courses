package com.salecoursecms.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleReponse {
    private Long id;
    private String roleName;
    private String description;
    private int status;
    private List<ModuleReponse> modules;
}
