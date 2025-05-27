package com.salecoursecms.dto.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String role;

    public UserInfoResponse(String username, String fullName, String phone, String roleName) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phone;
        this.role = roleName;
    }
}
