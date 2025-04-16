package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_SALESDETAIL_MODULER")
@Transactional
public class SalesDetailModular {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALESDETAIL_MODULER_ID")
    @SequenceGenerator(name = "SEQ_SALESDETAIL_MODULER_ID", sequenceName = "SEQ_SALESDETAIL_MODULER_ID", allocationSize = 1)
    @Column(name = "SALESDETAIL_MODULER_ID", nullable = false)
    private int id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SALES_DETAIL_ID")
    private SalesDetail salesDetail;
    
    @Column(name = "MODULER_NME")
    private String name;
    @Column(name = "MODULER_SERIAL")
    private String serial;

}
