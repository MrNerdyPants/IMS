package com.sys.ims.service;

import com.sys.ims.dto.RecordDTOs;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import com.sys.ims.model.NUser;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents the NUserService class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.service
 * @class NUserService
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/4/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/4/2025
 */
public interface NUserService {
   RecordDTOs.UserDto getMyProfile(String email) ;
    RecordDTOs.UserDto updateProfile(String email, RecordDTOs.UpdateUserProfileDto dto);
    void changePassword(String email, RecordDTOs.ChangePasswordRequest request) ;
    void uploadProfilePicture(String email, MultipartFile file) throws IOException;
    List<RecordDTOs.UserDto> getAllUsers(String userType, UUID clientId);

    void createUser(RecordDTOs.CreateUserDto dto) ;
    void assignRoleToUser(UUID userId, UUID roleId) ;

    void toggleUserStatus(UUID userId);

}
