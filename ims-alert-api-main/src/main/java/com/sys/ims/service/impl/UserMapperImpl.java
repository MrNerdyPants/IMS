package com.sys.ims.service.impl;

import com.sys.ims.dto.SignupRequestDTO;
import com.sys.ims.dto.SignupResponseDTO;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.model.NUser;
import com.sys.ims.service.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public NUser signupRequestToUser(SignupRequestDTO signupRequest) {
        NUser user = new NUser();

        // Set basic fields
        user.setId(UUID.randomUUID());
        user.setEmail(signupRequest.getEmail().toLowerCase().trim());
        user.setPhone(signupRequest.getPhone());
        user.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserType(signupRequest.getUserType());

        // Set name fields with proper handling
        if (signupRequest.getFullName() != null) {
            user.setFullName(signupRequest.getFullName().trim());
        } else {
            // Construct full name from first and last names if not provided
            String firstName = signupRequest.getFirstName() != null ?
                    signupRequest.getFirstName().trim() : "";
            String lastName = signupRequest.getLastName() != null ?
                    signupRequest.getLastName().trim() : "";
            user.setFullName((firstName + " " + lastName).trim());
        }

        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());

        // Set default status
        user.setStatus(UserStatus.ACTIVE); // or PENDING_VERIFICATION

        // Note: Relationships (client, branch, etc.) should be set separately
        // after validating/loading the related entities

        return user;
    }

    @Override
    public SignupResponseDTO mapToSignupResponseDTO(NUser user) {
        if (user == null) {
            return null;
        }

        SignupResponseDTO dto = new SignupResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setUserType(user.getUserType());
        dto.setStatus(user.getStatus());
        dto.setFullName(user.getFullName());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMessage("Signup successful");

        return dto;
    }
}
