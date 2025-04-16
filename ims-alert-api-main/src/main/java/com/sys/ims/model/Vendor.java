package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "IMS_VENDOR")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VENDOR_ID")
    @SequenceGenerator(name = "SEQ_VENDOR_ID", sequenceName = "SEQ_VENDOR_ID", allocationSize = 1)
    @Column(name = "VENDOR_ID")
    private int id;
    @Column(name = "VENDOR_NME")
    private String name;
    @Column(name = "VENDOR_SHORT_NME")
    private String shortName;
    @OneToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
    @OneToOne
    @JoinColumn(name = "PROVINCE_ID")
    private State state;
    @OneToOne
    @JoinColumn(name = "CITY_ID")
    private City city;
    @OneToOne
    @JoinColumn(name = "SITE_ID")
    private Site site;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "OFFICE_ADDRESS")
    private String officeAddress;
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "LAND_LINE")
    private String landLine;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<VendorContact> phone = new ArrayList<>();
    @Column(name = "AUTH_PERSON")
    private String authorizedPerson;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NTN_NBR")
    private String ntnNumber;
    @Column(name = "GST_NBR")
    private String gstNumber;
    @Column(name = "SCEP_NBR")
    private String secpNumber;
    @Column(name = "LOGIN_ID")
    private String loginId;
    @Column(name = "LOGIN_PASSWORD")
    private String loginPassword;
    @Lob
	@Column(name="LOGO")
	private byte[] logo;
    @Column(name = "FILE_NME")
    private String fileName; // Corrected typo from fileNme
    @Column(name = "FILE_SIZE")
    private String fileSize;
    @Column(name = "FILE_TYPE")
    private String fileType;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<VendorCategory> category = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "PREPARED_BY")
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy; // Renamed for clarity
    
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}