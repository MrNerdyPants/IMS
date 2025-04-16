package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IMS_HAZARD_SYMTOM")
public class Symtom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYMTOM_ID")
    @SequenceGenerator(name = "SEQ_SYMTOM_ID", sequenceName = "SEQ_SYMTOM_ID", allocationSize = 1)
    @Column(name = "SYMTOM_ID", nullable = false)
    private int id;

    @JsonIgnore
    @JoinColumn(name = "HAZARD_ID")
    @ManyToOne
    private Hazard hazard;

    @Column(name = "SYMTOM")
    private String symtom;
}
