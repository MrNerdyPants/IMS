package com.sys.ims.repository;

import com.sys.ims.model.NCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Represents the NCustomerRepository class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.repository
 * @class NCustomerRepository
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/10/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/10/2025
 */
@Repository
public interface NCustomerRepository extends JpaRepository<NCustomer, UUID> {
}
