package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_MAINTENANCE_USER")
public class MaintenanceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAINTENANCE_USER_ID")
    @SequenceGenerator(name = "SEQ_MAINTENANCE_USER_ID", sequenceName = "SEQ_MAINTENANCE_USER_ID", allocationSize = 1)
    @Column(name = "MAINTENANCE_USER_ID", nullable = false)
    private String id;
    @Column(name = "MAINTENANCE_ID", nullable = false)
    private String maintenaceId;

    @Column(name = "REF_USER_ID")
    private String userId;

}
