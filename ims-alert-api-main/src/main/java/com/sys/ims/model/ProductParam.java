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
@Table(name = "IMS_PARAM")
public class ProductParam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAM_ID")
    @SequenceGenerator(name = "SEQ_PARAM_ID", sequenceName = "SEQ_PARAM_ID", allocationSize = 1)
    @Column(name = "PARAM_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "TITLE")
    private String title;
    @Column(name = "PARAM_DESC")
    private String description;
    @Column(name = "PARAM_VAL")
    private String standerdValue;
    @Column(name = "MIN_RANG")
    private String minRange;
    @Column(name = "MAX_RANG")
    private String maxRange;

}
