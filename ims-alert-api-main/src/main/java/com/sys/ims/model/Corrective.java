package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMS_HAZARD_CORRECTIVE")
public class Corrective {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CORRECTIVE_ID")
    @SequenceGenerator(name = "SEQ_CORRECTIVE_ID", sequenceName = "SEQ_CORRECTIVE_ID", allocationSize = 1)
    @Column(name = "CORRECTIVE_ID", nullable = false)
    private int id;

    @JsonIgnore
    @JoinColumn(name = "HAZARD_ID")
    @ManyToOne
    private Hazard hazard;

    @Column(name = "CORRECTIVE")
    private String corrective;
}
