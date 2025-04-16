package com.sys.ims.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Table(name = "WEB_USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ID")
    @SequenceGenerator(name = "SEQ_USER_ID", sequenceName = "SEQ_USER_ID", allocationSize = 1)
    @Column(name = "USER_ID")
    private int id;
    @Column(name = "USER_NAME")
    private String name;
    @Column(name = "LOGIN_ID")
    private String username;
    @JsonIgnore
    @Column(name = "USER_PASSWORD")
    private String password;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
    @Column(name = "USER_PROFILE")
    private String profile;
    @Column(name = "USER_EMAIL")
    private String email;
    @Column(name = "USER_TYPE")
    private String type;
    
    // @JsonIgnore
    // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "USER_REF_ID") // EMPLOYEE ID
    private String refId;
    
    @Column(name = "FILE_NME")
    private String fileNme;
    
    @Column(name = "FILE_SIZE")
    private String fileSize;
    
    @Column(name = "FILE_TYPE")
    private String fileType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroup> groups;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSite> sites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRight> rights;
}
