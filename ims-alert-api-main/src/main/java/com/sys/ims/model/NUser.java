package com.sys.ims.model;

import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents the NUser class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.model
 * @class NUser
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
@Table(name = "nusers")
@NoArgsConstructor
@AllArgsConstructor
public class NUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String fullName;

    private String firstName;

    private String lastName;

    @Lob
    private String profilePicture;

    private String otpCode;
    private LocalDateTime otpExpiry;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private NDepartment department;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private NDesignation designation;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private NCustomer customer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<NRole> roles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

