package com.sys.ims.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "IMS_USER_SITE")
public class UserSite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_SITE_ID")
    @SequenceGenerator(name = "SEQ_USER_SITE_ID", sequenceName = "SEQ_USER_SITE_ID", allocationSize = 1)
    @Column(name = "USER_SITE_ID", nullable = false)
    private int id;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "SITE_ID")
    private Site site;
    
}
