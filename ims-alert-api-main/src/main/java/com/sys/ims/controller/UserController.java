package com.sys.ims.controller;

import com.sys.ims.dto.RecordDTOs;
import com.sys.ims.enums.UserType;
import com.sys.ims.service.NUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Represents the UserController class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.controller
 * @class UserController
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/2/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/2/2025
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final NUserService userService;

    // 🔐 Authenticated User - Get Profile
    @GetMapping("/me")
    public ResponseEntity<RecordDTOs.UserDto> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getMyProfile(userDetails.getUsername()));
    }

    // 🔐 Authenticated User - Update Profile
    @PutMapping("/me")
    public ResponseEntity<RecordDTOs.UserDto> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RecordDTOs.UpdateUserProfileDto updateRequest
    ) {
        return ResponseEntity.ok(userService.updateProfile(userDetails.getUsername(), updateRequest));
    }

    // 🔐 Change Password
    @PutMapping("/me/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RecordDTOs.ChangePasswordRequest request
    ) {
        userService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    // 📂 Optional: Upload Profile Picture
    @PostMapping("/me/upload-picture")
    public ResponseEntity<?> uploadPicture(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        userService.uploadProfilePicture(userDetails.getUsername(), file);
        return ResponseEntity.ok().build();
    }

    // 🛠️ Admin Only: Get All Users (filterable)
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<List<RecordDTOs.UserDto>> getAllUsers(
            @RequestParam(required = false) String userType,
            @RequestParam(required = false) UUID clientId
    ) {
        return ResponseEntity.ok(userService.getAllUsers(userType, clientId));
    }

    // 🛠️ Admin: Create New User
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<?> createUser(@RequestBody RecordDTOs.CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 🛠️ Admin: Assign Role
    @PostMapping("/{userId}/assign-role")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<?> assignRole(@PathVariable UUID userId, @RequestBody UUID roleId) {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok().build();
    }

    // 🛠️ Admin: Deactivate or Activate
    @PutMapping("/{userId}/toggle-status")
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    public ResponseEntity<?> toggleUserStatus(@PathVariable UUID userId) {
        userService.toggleUserStatus(userId);
        return ResponseEntity.ok().build();
    }


}
