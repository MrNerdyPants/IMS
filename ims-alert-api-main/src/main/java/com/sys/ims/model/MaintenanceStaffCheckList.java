package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_MAINTENANCE_SCLST")
public class MaintenanceStaffCheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAINTENANCE_SCLST_ID")
    @SequenceGenerator(name = "SEQ_MAINTENANCE_SCLST_ID", sequenceName = "SEQ_MAINTENANCE_SCLST_ID", allocationSize = 1)
    @Column(name = "MAINTENANCE_SCLST_ID", nullable = false)
    private String id;
    @Column(name = "MAINTENANCE_ID", nullable = false)
    private String maintenaceId;
    @Column(name = "PRODUCT", nullable = false)
    private String product;
    @Column(name = "SEARIAL", nullable = false)
    private String serial;
    @Column(name = "CHECKLIST_ID", nullable = false)
    private String checklistId;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "MAINTENANCE_SCLST_TYP", nullable = false)
    private String type;
    @Column(name = "MAINTENANCE_SCLST_VAL", nullable = false)
    private String value;
    @Column(name = "STD_VAL", nullable = false)
    private String stdValue;
    @Column(name = "MIN_RANGE", nullable = false)
    private String minRange;
    @Column(name = "MAX_RANGE", nullable = false)
    private String maxRange;
    @ElementCollection
    private List<String> checkList;
}
