package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "PROVINCE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROVINCE_ID")
    @SequenceGenerator(name = "SEQ_PROVINCE_ID", sequenceName = "SEQ_PROVINCE_ID", allocationSize = 1)
    @Column(name = "PROVINCE_ID", nullable = false)
    private int id;
    @Column(name = "PROVINCE_NME")
    private String name;

    @OneToOne( cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "countryId")
    private Country country;
}
