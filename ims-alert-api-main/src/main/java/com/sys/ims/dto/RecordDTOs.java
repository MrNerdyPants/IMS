package com.sys.ims.dto;

import java.util.UUID;

/**
 * Represents the RecordDTOs class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.dto
 * @class RecordDTOs
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/3/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/3/2025
 */
public class RecordDTOs {
    public record LoginRequest(String email, String password) {
    }

    public record RegisterRequest(String email, String password, String name, String userType) {
    }

    public record AuthResponse(String token, String userId, String userType) {
    }

//    public record UserDto(UUID id, String name, String email, String userType) {
//    }

    public record UserDto(
            UUID id,
            String name,
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            String userType,
            boolean active,
            String profileImageUrl
    ) {
    }

    public record UpdateUserProfileDto(
            String name,
            String phone,
            String address
    ) {
    }

    public record ChangePasswordRequest(
            String currentPassword,
            String newPassword
    ) {
    }

    public record CreateUserDto(
            String name,
            String email,
            String password,
            String userType, // SUPER_ADMIN, CLIENT_ADMIN, etc.
            UUID clientId // for Client Employee or Customer
    ) {
    }

    public record SignupRequest(
            String email,
            String phone,
            String password,
            String fullName,
            String profilePicture,
            String userType, // "CLIENT_ADMIN", "CLIENT_EMPLOYEE", etc.

            // Optional fields based on user type
            UUID clientId,         // For employees/customers
            UUID branchId,         // For employees
            UUID departmentId,     // For employees
            UUID designationId,    // For employees
            UUID customerId        // If creating internal user for a customer
    ) {}


}
