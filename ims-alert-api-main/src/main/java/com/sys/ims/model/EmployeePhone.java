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
@Table(name = "IMS_EMPLOYEE_PHONE")
public class EmployeePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLOYEE_PHONE_ID")
    @SequenceGenerator(name = "SEQ_EMPLOYEE_PHONE_ID", sequenceName = "SEQ_EMPLOYEE_PHONE_ID", allocationSize = 1)
    @Column(name = "EMPLOYEE_PHONE_ID", nullable = false)
    private int id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private  Employee employee;

    @Column(name = "PHONE_NO")
    private  String phoneNo;
}
