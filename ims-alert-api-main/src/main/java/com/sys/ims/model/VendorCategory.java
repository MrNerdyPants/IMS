package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_VENDOR_CATEGORY")
public class VendorCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VENDOR_CATEGORY_ID")
    @SequenceGenerator(name = "SEQ_VENDOR_CATEGORY_ID", sequenceName = "SEQ_VENDOR_CATEGORY_ID", allocationSize = 1)
    @Column(name = "VENDOR_CATEGORY_ID")
    private int id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "VENDOR_ID")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

}
