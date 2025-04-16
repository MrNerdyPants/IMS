package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "UNIT")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UNIT_ID")
    @SequenceGenerator(name = "SEQ_UNIT_ID", sequenceName = "SEQ_UNIT_ID", allocationSize = 1)
    @Column(name = "UNIT_ID", nullable = false)
    private int id;

    @Column(name = "TITLE")
    private String name;
}
