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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_VENDOR_PHONE")
public class VendorContact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VENDOR_PHONE_ID")
    @SequenceGenerator(name = "SEQ_VENDOR_PHONE_ID", sequenceName = "SEQ_VENDOR_PHONE_ID", allocationSize = 1)
    @Column(name = "VENDOR_PHONE_ID")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "VENDOR_ID")
    private Vendor vendor;

    @Column(name = "PHONE_NO")
    private String phoneNo;
}
