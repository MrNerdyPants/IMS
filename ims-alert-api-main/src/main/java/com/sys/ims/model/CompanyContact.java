package com.sys.ims.model;

import javax.persistence.CascadeType;
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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMPANY_PHONE")
public class CompanyContact {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHONE_ID")
	@SequenceGenerator(name = "SEQ_PHONE_ID", sequenceName = "SEQ_PHONE_ID", allocationSize = 1)
	@Column(name = "PHONE_ID", nullable = false)
	private int id;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	@Column(name = "PHONE_NBR")
	private String contactNo;
}
