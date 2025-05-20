package com.sys.ims.service;

import com.sys.ims.dto.RecordDTOs.SignupRequest;
import com.sys.ims.enums.UserType;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.NUser;
import org.apache.coyote.BadRequestException;

/**
 * Represents the SignupStrategy class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.service
 * @class SignupStrategy
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/10/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/10/2025
 */
public interface SignupStrategy {
    boolean supports(UserType userType);

    void signup(SignupRequest request, NUser creator) throws BadRequestException, BaseException;
}

