package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "IMS_DEPARTMENT")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DEPARTMENT_ID")
    @SequenceGenerator(name = "SEQ_DEPARTMENT_ID", sequenceName = "SEQ_DEPARTMENT_ID", allocationSize = 1)
    @Column(name = "DEPARTMENT_ID")
    private int id;
    @Column(name="DEPARTMENT_NME")
    private String name;

}
