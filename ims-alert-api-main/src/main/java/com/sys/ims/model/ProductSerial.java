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
@Table(name = "IMS_PROD_SERIAL")
public class ProductSerial {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SERIAL_ID")
    @SequenceGenerator(name = "SEQ_SERIAL_ID", sequenceName = "SEQ_SERIAL_ID", allocationSize = 1)
    @Column(name = "IMS_SERIAL_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "TITLE")
    private String title;
}
