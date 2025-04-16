package com.sys.ims.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "IMS_MNFCTURR_CATEGORY")
public class ManufacturerCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MNFCTURR_CATEGORY_ID")
    @SequenceGenerator(name = "SEQ_MNFCTURR_CATEGORY_ID", sequenceName = "SEQ_MNFCTURR_CATEGORY_ID", allocationSize = 1)
    @Column(name = "MNFCTURR_CATEGORY_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MANUFACTURER_ID", nullable = false) // Foreign key to Manufacturer
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false) // Foreign key to Category
    private Category category;
}
