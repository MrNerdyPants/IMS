package com.sys.ims.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "ROLE_RIGHT")
public class GroupRight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE_RIGHT_ID")
	@SequenceGenerator(name = "SEQ_ROLE_RIGHT_ID", sequenceName = "SEQ_ROLE_RIGHT_ID", allocationSize = 1)
	@Column(name = "ROLE_RIGHT_ID", nullable = false)
    private int id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RIGHT_ID")
    private Right right;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Group group;
    @Column(name = "CAN_EDIT")
    private String canEdit;
    @Column(name = "CAN_DELETE")
    private String canDelete;
    @Column(name = "CAN_EXPORT")
    private String canExport;
}
