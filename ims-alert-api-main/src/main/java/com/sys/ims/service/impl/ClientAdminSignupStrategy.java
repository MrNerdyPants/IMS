package com.sys.ims.service.impl;

import com.sys.ims.dto.RecordDTOs.SignupRequest;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NUser;
import com.sys.ims.repository.NRoleRepository;
import com.sys.ims.repository.NUserRepository;
import com.sys.ims.service.SignupStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents the ClientAdminSignupStrategy class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.service.impl
 * @class ClientAdminSignupStrategy
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/10/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/10/2025
 */
@Service
public class ClientAdminSignupStrategy implements SignupStrategy {

    @Autowired
    private NUserRepository userRepository;
    @Autowired
    private NRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(UserType userType) {
        return userType == UserType.CLIENT_ADMIN;
    }

    @Override
    public void signup(SignupRequest request, NUser creator) throws BaseException {
        if (creator == null || creator.getUserType() != UserType.SUPER_ADMIN) {
            throw new AccessDeniedException("Only SuperAdmin can create ClientAdmins");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BaseException("Email already taken");
        }

        NUser newUser = NUser.builder()
                .email(request.email())
                .fullName(request.fullName())
                .phone(request.phone())
                .passwordHash(passwordEncoder.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .userType(UserType.CLIENT_ADMIN)
                .roles(Set.of(roleRepository.findByName("CLIENT_ADMIN").orElseThrow()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(newUser);
    }
}

