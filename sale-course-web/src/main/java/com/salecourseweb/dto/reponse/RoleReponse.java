package com.salecourseweb.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleReponse {
    private Long id;
    private String roleName;
    private String description;
    private int status;
    private List<ModuleReponse> modules;
}
