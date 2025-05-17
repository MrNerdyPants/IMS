package com.sys.ims.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.ims.dto.*;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import com.sys.ims.exception.AuthenticationFailedException;
import com.sys.ims.exception.UserAlreadyExistsException;
import com.sys.ims.model.NUser;
import com.sys.ims.repository.NUserRepository;
import com.sys.ims.repository.UserRepository;
import com.sys.ims.service.UserMapper;
import com.sys.ims.util.JwtTokenUtil;
import com.sys.ims.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

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

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

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



    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Get additional user details
            NUser user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new AuthenticationFailedException("User not found"));

            final String token = jwtTokenUtil.generateToken(userDetails, user.getUserType(), user.getFullName());

            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);
            response.setEmail(user.getEmail());
            response.setUserType(user.getUserType());
            response.setFullName(user.getFullName());
            response.setMessage("Authentication successful");

            return response;
        } catch (Exception e) {
            throw new AuthenticationFailedException("Authentication failed: " + e.getMessage());
        }
    }



    public void register(RecordDTOs.RegisterRequest request) {
        NUser user = new NUser();
        user.setEmail(request.email());
        user.setFullName(request.name());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setUserType(UserType.valueOf(request.userType()));

        userRepository.save(user);
    }


    @Transactional
    public SignupResponseDTO registerUser(SignupRequestDTO signupRequest) throws UserAlreadyExistsException {
        // Check if user already exists with this email
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + signupRequest.getEmail() + " already exists");
        }

        // Map DTO to entity
        NUser user = userMapper.signupRequestToUser(signupRequest);

        // Set additional fields
        user.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));
        user.setStatus(UserStatus.ACTIVE); // or PENDING_VERIFICATION based on your requirements

        // Save user
        NUser savedUser = userRepository.save(user);

        // Map entity to response DTO
        SignupResponseDTO response = userMapper.mapToSignupResponseDTO(savedUser);
        response.setMessage("User registered successfully");

        return response;
    }


}

