package com.sys.ims.dto;


import com.sys.ims.enums.UserType;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String email;
    private UserType userType;
    private String fullName;
    private String message;
}