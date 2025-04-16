package com.sys.ims.model;

import java.time.LocalDate;

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
@Table(name = "IMS_PART")
public class ProductPart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PART_ID")
    @SequenceGenerator(name = "SEQ_PART_ID", sequenceName = "SEQ_PART_ID", allocationSize = 1)
    @Column(name = "PART_ID", nullable = false)
    
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    
    @Column(name = "TITLE")
    private String title;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "PART_LEFT")
    private String life;
    @Column(name = "UNIT")
    private String unit;
    @Column(name = "PART_PERIOD")
    private String period;

    @Column(name = "PART_DATE")
    private LocalDate date;
    @Column(name = "PART_VAL")
    private String value;
    @Column(name = "PART_READING")
    private String reading;
}
