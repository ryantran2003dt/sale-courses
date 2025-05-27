package com.salecoursecms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "username.required")
    private String username;
    @NotBlank(message = "password.required")
    private String password;
    @NotBlank(message = "fullname.required")
    private String fullName;
    @NotNull(message = "gender.required")
    private int gender;
    @NotBlank(message = "phone.required")
    private String phone;
    @NotNull(message = "dob.required")
    private Date dateOfBirth;
    @NotBlank(message = "email.required")
    @Email(message = "email.invalid")
    private String email;
    @NotBlank(message = "img.required")
    private String img;
    private Date createDate;
    private Date updateDate;
    @NotNull(message = "status.required")
    private int status = 1;

    @NotNull(message = "roleId.required")
    private Long roldId;

}
