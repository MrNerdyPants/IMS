package com.sys.ims.service.impl;

import com.sys.ims.dto.RecordDTOs.*;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import com.sys.ims.model.Client;
import com.sys.ims.model.NRole;
import com.sys.ims.model.NUser;
import com.sys.ims.repository.NRoleRepository;
import com.sys.ims.repository.NUserRepository;
import com.sys.ims.service.NUserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents the NUserServiceImpl class in the ims-alert-api-main project.
 *
 * @project ims-alert-api-main
 * @module com.sys.ims.service.impl
 * @class NUserServiceImpl
 * @author Kashan Asim
 * @version 1.0
 * @since 5/4/2025
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/4/2025
 * 
 * @license Licensed under the Apache License, Version 2.0
 * 
 * @description A brief description of the class functionality.
 * 
 * @notes
 * <ul>
 *     <li>Provide any additional notes or remarks here.</li>
 * </ul>
 */
@Service
public class NUserServiceImpl implements NUserService {

    private final NUserRepository userRepository;
    private final NRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public NUserServiceImpl(NUserRepository userRepository, NRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getMyProfile(String email) {
        NUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    @Override
    public UserDto updateProfile(String email, UpdateUserProfileDto dto) {
        NUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(dto.name());
        user.setPhone(dto.phone());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return mapToDto(user);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        NUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void uploadProfilePicture(String email, MultipartFile file) throws IOException {
        NUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        byte[] image = Base64.encodeBase64(file.getBytes());
        String base64Image = new String(image);
        user.setProfilePicture(base64Image);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers(String userType, UUID clientId) {
        List<NUser> users;
        if (userType != null && clientId != null) {
            users = userRepository.findByUserTypeAndClientId(UserType.valueOf(userType), clientId);
        } else if (clientId != null) {
            users = userRepository.findByClientId(clientId);
        } else {
            users = userRepository.findAll();
        }
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createUser(CreateUserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already registered");
        }

        NUser user = NUser.builder()
                .email(dto.email())
                .fullName(dto.name())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .userType(UserType.valueOf(dto.userType()))
                .status(UserStatus.ACTIVE)
                .client(dto.clientId() != null ? Client.builder().id(dto.clientId()).build() : null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public void assignRoleToUser(UUID userId, UUID roleId) {
        NUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        NRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void toggleUserStatus(UUID userId) {
        NUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getStatus() == UserStatus.ACTIVE) {
            user.setStatus(UserStatus.INACTIVE);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    private UserDto mapToDto(NUser user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getUserType().name(),
                user.getStatus() == UserStatus.ACTIVE,
                user.getProfilePicture()
        );
    }
}
