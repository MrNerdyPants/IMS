package com.sys.ims.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.ims.dto.RecordDTOs;
import com.sys.ims.enums.UserType;
import com.sys.ims.model.NUser;
import com.sys.ims.repository.NUserRepository;
import com.sys.ims.repository.UserRepository;
import com.sys.ims.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Represents the NUserServiceImpl class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.service.impl
 * @class NUserServiceImpl
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/3/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/3/2025
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final NUserRepository userRepository;
    private final JwtUtil jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    void init(){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public RecordDTOs.AuthResponse login(RecordDTOs.LoginRequest request) {
        NUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtProvider.generateToken(mapper.convertValue(user, UserDetailsImpl.class));
        return new RecordDTOs.AuthResponse(token, user.getId().toString(), user.getUserType().name());
    }

    public void register(RecordDTOs.RegisterRequest request) {
        NUser user = new NUser();
        user.setEmail(request.email());
        user.setFullName(request.name());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setUserType(UserType.valueOf(request.userType()));

        userRepository.save(user);
    }
}

