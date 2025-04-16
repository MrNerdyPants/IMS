package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "IMS_DESIGNATION")
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DESIGNATION_ID")
    @SequenceGenerator(name = "SEQ_DESIGNATION_ID", sequenceName = "SEQ_DESIGNATION_ID", allocationSize = 1)
    @Column(name = "DESIGNATION_ID")
    private int id;
    @Column(name="DESIGNATION_NME")
    private String name;

}
