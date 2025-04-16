package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "IMS_MAINTENANCE_DETAIL")
public class MaintenanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAINTENANCE_DETAIL_ID")
    @SequenceGenerator(name = "SEQ_MAINTENANCE_DETAIL_ID", sequenceName = "SEQ_MAINTENANCE_DETAIL_ID", allocationSize = 1)
    @Column(name = "MAINTENANCE_DETAIL_ID", nullable = false)
    private int id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MAINTENANCE_ID")
    private Maintenance maintenace;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Column(name = "SEARIAL")
    private String serial;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHECKLSTLST_ID")
    private CheckListList checkListList;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "MAINTENANCE_DETAIL_TYP")
    private String type;
    @Column(name = "MAINTENANCE_DETAIL_VAL")
    private String value;
    @Column(name = "STD_VAL")
    private String stdValue;
    @Column(name = "MIN_RANGE")
    private String minRange;
    @Column(name = "MAX_RANGE")
    private String maxRange;
}
