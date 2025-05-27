package com.salecoursecms.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReponse {
    private Long id;
    private String username;
    private String token;
    private RoleReponse role;
}
