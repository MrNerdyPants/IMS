package com.sys.ims.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.List;

@Entity(name = "country")
@Table(name = "COUNTRY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUNTRY_ID")
    @SequenceGenerator(name = "SEQ_COUNTRY_ID", sequenceName = "SEQ_COUNTRY_ID", allocationSize = 1)
    @Column(name = "COUNTRY_ID", nullable = false)
    private int id;
    @Column(name = "COUNTRY_NME")
    private String name;
}
