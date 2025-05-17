package com.sys.ims.service;


import com.sys.ims.dto.SignupRequestDTO;
import com.sys.ims.dto.SignupResponseDTO;
import com.sys.ims.model.NUser;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper {
    NUser signupRequestToUser(SignupRequestDTO signupRequest);

     SignupResponseDTO mapToSignupResponseDTO(NUser user);
}