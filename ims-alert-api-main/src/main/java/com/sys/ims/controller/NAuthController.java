package com.sys.ims.controller;

import com.sys.ims.dto.*;
import com.sys.ims.exception.AuthenticationFailedException;
import com.sys.ims.exception.UserAlreadyExistsException;
import com.sys.ims.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Represents the NAuthController class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.controller
 * @class NAuthController
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
@RequestMapping("/api/nauth")
@RequiredArgsConstructor
public class NAuthController {

    private final AuthServiceImpl authService;

//    @PostMapping("/login")
//    public ResponseEntity<RecordDTOs.AuthResponse> login(@RequestBody RecordDTOs.LoginRequest request) {
//        return ResponseEntity.ok(authService.login(request));
//    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RecordDTOs.RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequest) {
        try {
            SignupResponseDTO response = authService.registerUser(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

