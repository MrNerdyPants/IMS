package com.sys.ims.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "RIGHTS")
public class Right {
    
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RIGHT_ID")
	@SequenceGenerator(name = "SEQ_RIGHT_ID", sequenceName = "SEQ_RIGHT_ID", allocationSize = 1)
	@Column(name = "RIGHT_ID", nullable = false)
    private int id;
    
    @Column(name = "RIGHT_TXT")
    private String name;
    
    @Column(name = "URL")
    private String url;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "RIGHT_ID")
    private Right parent;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("sort ASC")
    private Set<Right> children = new HashSet<Right>();
    
    @Column(name = "SORT")
    private Integer sort;

    @Column(name = "ACTIVE_IND")
    private String activeInd;
    
    @Column(name = "ICON")
    private String icon;
    
    @Column(name = "DISPLAY_IND")
    private String displayInd;
}
