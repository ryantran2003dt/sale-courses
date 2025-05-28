package com.salecourseweb.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReponse {
    private Long id;
    private String email;
    private String token;
    private RoleReponse role;
}
