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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "IMS_COMPANY_CATEGORY")

public class CompanyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPANY_CATEGORY_ID")
    @SequenceGenerator(name = "SEQ_COMPANY_CATEGORY_ID", sequenceName = "SEQ_COMPANY_CATEGORY_ID", allocationSize = 1)
    @Column(name = "COMPANY_CATEGORY_ID")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID") // Foreign key to Manufacturer
    private Company company;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID") // Foreign key to Category
    private Category category;
}
