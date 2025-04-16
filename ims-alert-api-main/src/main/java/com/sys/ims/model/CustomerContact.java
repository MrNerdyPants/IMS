package com.sys.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMS_CUSTOMER_PHONE")
public class CustomerContact {
@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER_PHONE_ID")
    @SequenceGenerator(name = "SEQ_CUSTOMER_PHONE_ID", sequenceName = "SEQ_CUSTOMER_PHONE_ID", allocationSize = 1)
    @Column(name = "CUSTOMER_PHONE_ID", nullable = false)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "PHONE_NO")
    private String phoneNo;
}
