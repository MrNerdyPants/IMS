package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SITE_ID")
    @SequenceGenerator(name = "SEQ_SITE_ID", sequenceName = "SEQ_SITE_ID", allocationSize = 1)
    @Column(name = "SITE_ID", nullable = false)
    private int id;
    
    @Column(name = "SITE_NME")
    private String name;
    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "PROVINCE_ID")
    private State state;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "cityId")
    private City city;

    @Column(name = "PREPARED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @JsonIgnore
    @Column(name = "COMPANY_ID")
    private String companyId;

    @OneToMany(mappedBy = "site", fetch = FetchType.LAZY)
    private List<SitePhone> phone = new ArrayList<>();
}
