package com.sys.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "IMS_HAZARD_TYPE")
public class HazardType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HAZARD_TYPE_ID")
    @SequenceGenerator(name = "SEQ_HAZARD_TYPE_ID", sequenceName = "SEQ_HAZARD_TYPE_ID", allocationSize = 1)
    @Column(name = "HAZARD_TYPE_ID", nullable = false)
    private int id;

    @Column(name = "TITLE")
    private String name;
}
