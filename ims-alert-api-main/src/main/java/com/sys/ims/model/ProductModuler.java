package com.sys.ims.model;

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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IMS_MODULAR")
public class ProductModuler {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODUALR_ID")
    @SequenceGenerator(name = "SEQ_MODUALR_ID", sequenceName = "SEQ_MODUALR_ID", allocationSize = 1)
    @Column(name = "MODULAR_ID", nullable = false)
    private int id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Column(name = "TITLE")
    private String title;
    @Column(name = "SERIAL")
    private String serial;
    @Column(name = "BARCODE")
    private String barCode;
    @Column(name = "WARRENTY")
    private String warranty;
}
