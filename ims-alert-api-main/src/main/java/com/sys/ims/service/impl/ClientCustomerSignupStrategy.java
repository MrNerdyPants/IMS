package com.sys.ims.service.impl;

import com.sys.ims.dto.RecordDTOs;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NCustomer;
import com.sys.ims.model.NUser;
import com.sys.ims.repository.NCustomerRepository;
import com.sys.ims.repository.NRoleRepository;
import com.sys.ims.repository.NUserRepository;
import com.sys.ims.service.SignupStrategy;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents the ClientCustomerSignupStrategy class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.service.impl
 * @class ClientCustomerSignupStrategy
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
public class ClientCustomerSignupStrategy implements SignupStrategy {

    @Autowired
    private NUserRepository userRepository;
    @Autowired private NCustomerRepository customerRepository;
    @Autowired private NRoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(UserType userType) {
        return userType == UserType.CLIENT_CUSTOMER;
    }

    @Override
    public void signup(RecordDTOs.SignupRequest request, NUser creator) throws BadRequestException, BaseException {
        if (request.clientId() == null) {
            throw new BadRequestException("Client info is required for customer signup");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BaseException("Email already registered");
        }

        NCustomer customer = NCustomer.builder()
                .client(request.clientId())
                .fullName(request.fullName())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .nationalId(request.nationalId())
                .createdAt(LocalDateTime.now())
                .build();

        customerRepository.save(customer);

        NUser newUser = NUser.builder()
                .email(request.email())
                .fullName(request.fullName())
                .phone(request.phone())
                .passwordHash(passwordEncoder.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .userType(UserType.CLIENT_CUSTOMER)
                .client(request.clientId())
                .customer(customer)
                .roles(Set.of(roleRepository.findByName("CLIENT_CUSTOMER").orElseThrow()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(newUser);
    }
}
