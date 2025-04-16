package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "IMS_MANUFACTURER")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MANUFACTURER_ID")
    @SequenceGenerator(name = "SEQ_MANUFACTURER_ID", sequenceName = "SEQ_MANUFACTURER_ID", allocationSize = 1)
    @Column(name = "MANUFACTURER_ID", nullable = false)
    private int id;

    @Column(name = "MANUFACTURER_NME")
    private String name;
    @Column(name = "MANUFACTURER_SHORT_NME")
    private String shortName;
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "LAND_LINE")
    private String landLine;
    @Column(name = "WEB_LINK")
    private String webLink;
    @Column(name = "INTRODUCE")
    private String introduce;
    @Lob
    @Column(name = "LOGO")
    private byte[] logo;
    @Column(name = "FILE_NME")
    private String fileNme;
    @Column(name = "FILE_SIZE")
    private String fileSize;
    @Column(name = "FILE_TYPE")
    private String fileType;

    @OneToMany(mappedBy = "manufacturer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ManufacturerCategory> product = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy;
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
