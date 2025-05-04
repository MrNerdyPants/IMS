package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

/**
 * Represents the NRoles class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.model
 * @class NRoles
 * @lastModifiedBy Kashan.Asim
 * @lastModifiedDate 5/2/2025
 * @license Licensed under the Apache License, Version 2.0
 * @description A brief description of the class functionality.
 * @notes <ul>
 * <li>Provide any additional notes or remarks here.</li>
 * </ul>
 * @since 5/2/2025
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nroles")
public class NRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name; // e.g., "CLIENT_ADMIN", "EMPLOYEE_TICKETING"

    @ManyToOne
    private Client client; // Null for SuperAdmin/global roles

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    // Getters and setters
}

