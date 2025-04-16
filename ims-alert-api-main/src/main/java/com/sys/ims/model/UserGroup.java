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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMS_USER_ROLE")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-group-seq-generator")
    @SequenceGenerator(name = "user-group-seq-generator", sequenceName = "SEQ_USER_ROLE_ID", allocationSize = 1)
    @Column(name = "USER_ROLE_ID", nullable = false)
    private int Id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Group group;
}
