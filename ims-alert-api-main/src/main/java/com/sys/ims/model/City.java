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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CITY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITY_ID")
    @SequenceGenerator(name = "SEQ_CITY_ID", sequenceName = "SEQ_CITY_ID", allocationSize = 1)
    @Column(name = "CITY_ID", nullable = false)
    private int id;
    @Column(name = "CITY_NME")
    private String name;
    @ManyToOne( cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "countryId")
    private Country country;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "provinceId")
    private State state;
}
