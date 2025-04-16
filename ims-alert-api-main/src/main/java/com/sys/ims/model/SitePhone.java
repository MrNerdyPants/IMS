package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_SITE_PHONE")
public class SitePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SITE_PHONE_ID")
    @SequenceGenerator(name = "SEQ_SITE_PHONE_ID", sequenceName = "SEQ_SITE_PHONE_ID", allocationSize = 1)
    @Column(name = "SITE_PHONE_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "SITE_ID")
    private Site site;

    @Column(name = "PHONE_NO")
    private String phoneNo;
}
