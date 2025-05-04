package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents the NCustomer class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.model
 * @class NCustomer
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
@Table(name = "ncustomers")
public class NCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String nationalId;

    private LocalDateTime createdAt;

}

