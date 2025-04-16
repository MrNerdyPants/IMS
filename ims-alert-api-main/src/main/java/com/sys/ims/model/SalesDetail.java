package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IMS_SALES_DETAIL")
@Transactional
public class SalesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALES_DETAIL_ID")
    @SequenceGenerator(name = "SEQ_SALES_DETAIL_ID", sequenceName = "SEQ_SALES_DETAIL_ID", allocationSize = 1)
    @Column(name = "SALES_DETAIL_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SALES_ID")
    private Sales sale;
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_TYP")
    private ProductType productType;
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT")
    private Product product;
    
    @Column(name = "PROD_MODEL")
    private String model;
    @Column(name = "SERIAL_NO")
    private String serial;
    @Column(name = "REFERENCE_NO")
    private String referenceNo;

    @OneToMany(mappedBy = "salesDetail", cascade = CascadeType.ALL)
    private List<SalesDetailModular> mudulerSerial = new ArrayList<>();
}
