package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "IMS_FEATURE")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FEATURE_ID")
    @SequenceGenerator(name = "SEQ_FEATURE_ID", sequenceName = "SEQ_FEATURE_ID", allocationSize = 1)
    @Column(name = "IMS_FEATURE_ID", nullable = false)
    private int id;

    @Column(name = "FEATURE_NME")
    private String name;

    @Column(name = "FEATURE_MODEL")
    private String model;

    @Column(name = "REF_CATEGORY_ID")
    private String subCategory;

    @Column(name = "FEATURE_ICON")
    private String icon;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
