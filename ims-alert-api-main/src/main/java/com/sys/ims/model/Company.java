package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPANY_ID")
    @SequenceGenerator(name = "SEQ_COMPANY_ID", sequenceName = "SEQ_COMPANY_ID", allocationSize = 1)
    @Column(name = "COMPANY_ID", nullable = false)
    private int id;

    @Column(name = "COMPANY_NME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROVINCE_ID")
    private State state;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "OFFICE_ADDRESS")
    private String officeAddress;
    @Column(name = "LAND_LINE")
    private String landLine;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY)
    private List<CompanyContact> phone = new ArrayList<CompanyContact>();

    @Column(name = "AUTH_PERSON")
    private String authorizedPerson;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NTN_NBR")
    private String ntnNumber;
    @Column(name = "GST_NBR")
    private String gstNumber;
    @Column(name = "SECP_NBR")
    private String secpNumber;
    @Column(name = "LOGIN_ID")
    private String loginId;
    @Column(name = "LOGIN_PASSWORD")
    private String loginPassword;

    @Lob
    @Column(name = "LOGO")
    private byte[] logo;
    @Column(name = "FILE_NME")
    private String fileNme;
    @Column(name = "FILE_SIZE")
    private String fileSize;
    @Column(name = "FILE_TYP")
    private String fileType;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<User> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<CompanyCategory> category = new ArrayList<>();
}
