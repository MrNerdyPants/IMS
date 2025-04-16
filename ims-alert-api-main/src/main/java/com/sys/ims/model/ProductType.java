package com.sys.ims.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Embeddable // Marking this class as embeddable for JPA
@Getter
@Setter
@Entity
@Table(name = "IMS_PRODUCT_TYPE")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_TYPE_ID")
    @SequenceGenerator(name = "SEQ_PRODUCT_TYPE_ID", sequenceName = "SEQ_PRODUCT_TYPE_ID", allocationSize = 1)
    @Column(name = "PRODUCT_TYPE_ID", nullable = false)
    private int id;

    @JsonIgnore
    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne
    private Category category;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CODE")
    private String code;

    @Column(name = "TYPE_NATURE")
    private String typeNature;
}