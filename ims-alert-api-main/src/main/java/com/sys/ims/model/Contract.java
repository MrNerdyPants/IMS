package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
@Entity
@Table(name = "IMS_CONTRACT")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTRACT_ID")
    @SequenceGenerator(name = "SEQ_CONTRACT_ID", sequenceName = "SEQ_CONTRACT_ID", allocationSize = 1)
    @Column(name = "CONTRACT_ID", nullable = false)
    private int id;

    @Column(name = "CONTRACT_DTE")
    private LocalDate date;

    @Column(name = "CONTRACT_EXPIRY")
    private LocalDate expiry;

    @Column(name = "CONTRACT_PERIOD")
    private String period;
    
    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractDetail> detail = new ArrayList<>();
    
}
