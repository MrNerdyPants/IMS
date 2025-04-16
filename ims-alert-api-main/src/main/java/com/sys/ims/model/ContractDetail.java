package com.sys.ims.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IMS_CONTRACT_DETAIL")
public class ContractDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTRACT_DETAIL_ID")
    @SequenceGenerator(name = "SEQ_CONTRACT_DETAIL_ID", sequenceName = "SEQ_CONTRACT_DETAIL_ID", allocationSize = 1)
    @Column(name = "CONTRACT_DETAIL_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contract contract;

    @Column(name = "CONTRACT_DETAIL_TYPE")
    private String type;

    @Column(name = "PAYMENT_TERM")
    private String paymentTerm;

    @Column(name = "NO_OF_VISITS")
    private String noOfVisits;

    @Column(name = "AMOUNT")
    private String amount;
}
