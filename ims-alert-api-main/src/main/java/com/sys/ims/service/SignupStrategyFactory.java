package com.sys.ims.service;

import com.sys.ims.enums.UserType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Represents the SignupStrategyFactory class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.service
 * @class SignupStrategyFactory
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/10/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/10/2025
 */
@Component
public class SignupStrategyFactory {

    private final List<SignupStrategy> strategies;

    public SignupStrategyFactory(List<SignupStrategy> strategies) {
        this.strategies = strategies;
    }

    public SignupStrategy getStrategy(UserType userType) {
        return strategies.stream()
                .filter(s -> s.supports(userType))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported user type"));
    }
}

