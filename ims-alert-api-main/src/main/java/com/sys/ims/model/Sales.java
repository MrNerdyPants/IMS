package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "IMS_SALES")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALES_ID")
    @SequenceGenerator(name = "SEQ_SALES_ID", sequenceName = "SEQ_SALES_ID", allocationSize = 1)
    @Column(name = "SALES_ID", nullable = false)
    private int id;

    @Column(name = "SALE_DTE")
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SalesDetail> detail = new ArrayList<>();

}
