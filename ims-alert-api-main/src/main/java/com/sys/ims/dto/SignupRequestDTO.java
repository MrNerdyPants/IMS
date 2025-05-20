package com.sys.ims.dto;


import com.sys.ims.enums.UserType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class SignupRequestDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "User type is required")
    private UserType userType;

    private String firstName;
    private String lastName;
    private String fullName;

    // Optional fields for relationships
    private UUID clientId;
    private UUID branchId;
    private UUID departmentId;
    private UUID designationId;
    private UUID customerId;
}
