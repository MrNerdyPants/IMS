package com.sys.ims.repository;

import com.sys.ims.model.Client;
import com.sys.ims.model.NRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents the NRoleReository class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.repository
 * @class NRoleReository
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/4/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/4/2025
 */
@Repository
public interface NRoleRepository extends JpaRepository<NRole, UUID> {

    // Find by exact name
    Optional<NRole> findByName(String name);

    // Find all roles for a specific client
    List<NRole> findByClient(Client client);

    // Find a role by name and client
    Optional<NRole> findByNameAndClient(String name, Client client);

    // Get all global roles (where client is null, for super admin)
    @Query("SELECT r FROM NRole r WHERE r.client IS NULL")
    List<NRole> findGlobalRoles();

    // Get all roles with a specific permission code
    @Query("SELECT r FROM NRole r JOIN r.permissions p WHERE p.code = :permissionCode")
    List<NRole> findByPermissionCode(String permissionCode);

}
