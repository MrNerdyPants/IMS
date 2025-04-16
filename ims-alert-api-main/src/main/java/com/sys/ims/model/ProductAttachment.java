package com.sys.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "IMS_PROD_ATTACMENT")
public class ProductAttachment {
@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROD_ATTACHMENT_ID")
    @SequenceGenerator(name = "SEQ_PROD_ATTACHMENT_ID", sequenceName = "SEQ_PROD_ATTACHMENT_ID", allocationSize = 1)
    @Column(name = "PROD_ATTACHMENT_ID", nullable = false)
    private int id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Lob
    @Column(name = "FILE_DATA")
    private byte[] fileData;

    @Column(name = "FILE_NAME")
    private String fileNme;
    
    @Column(name = "FILE_SIZE")
    private String fileSize;
    
    @Column(name = "FILE_DESC")
    private String documentDesc;

    @Column(name = "FILE_TYPE")
    private String contentType;
}
