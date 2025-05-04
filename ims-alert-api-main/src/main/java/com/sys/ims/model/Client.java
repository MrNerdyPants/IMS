package com.sys.ims.model;

import com.sys.ims.enums.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents the Client class in the ims-alert-api-main project.
 *
 * @author Kashan Asim
 * @version 1.0
 * @project ims-alert-api-main
 * @module com.sys.ims.model
 * @class Client
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
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String companyName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @ElementCollection
    @CollectionTable(name = "client_modules", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "module")
    private List<String> modulesEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
