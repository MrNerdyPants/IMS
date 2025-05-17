package com.sys.ims.dto;

import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import lombok.Data;

import java.util.UUID;

@Data
public class SignupResponseDTO {
    private UUID id;
    private String email;
    private String phone;
    private UserType userType;
    private UserStatus status;
    private String fullName;
    private String firstName;
    private String lastName;
    private String message;
}