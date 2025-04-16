package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "IMS_EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLOYEE_ID")
    @SequenceGenerator(name = "SEQ_EMPLOYEE_ID", sequenceName = "SEQ_EMPLOYEE_ID", allocationSize = 1)
    
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private int id;
    
    @Column(name = "EMPLOYEE_NME")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
    
    @ManyToOne
    @JoinColumn(name = "DESIGNATION_ID")
    private Designation designation;
    
    @ManyToOne
    @JoinColumn(name = "SITE_ID")
    private Site site;
    
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "LOGIN_ID")
    private String loginId;
    @Column(name = "LOGIN_PSWD")
    private String loginPassword;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy;
    
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeePhone> phone = new ArrayList<>();
}
