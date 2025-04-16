package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "IMS_ROLE") // Same like as Role in ERP
public class Group  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group-seq-generator")
    @SequenceGenerator(name = "group-seq-generator", sequenceName = "SEQ_ROLE_ID", allocationSize = 1)
    @Column(name = "ROLE_ID", nullable = false)
    private int id;
    @Column(name = "ROLE_NME")
    private String name;
    @Column(name = "ROLE_ACTIVE_IND")
    private String activeInd;
    
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<GroupRight> rights = new ArrayList<GroupRight>();
}
