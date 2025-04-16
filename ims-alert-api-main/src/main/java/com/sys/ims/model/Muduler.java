package com.sys.ims.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

//Muduler class as a separate entity
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "IMS_PURCHASEDETAIL_MODULER")
public class Muduler {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PURCHASEDETAIL_MODULER_ID")
	@SequenceGenerator(name = "SEQ_PURCHASEDETAIL_MODULER_ID", sequenceName = "SEQ_PURCHASEDETAIL_MODULER_ID", allocationSize = 1)
	@Column(name = "PURCHASEDETAIL_MODULER_ID", nullable = false)
	private int id; // Change to Long for better scalability

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.DETACH })
	@JoinColumn(name = "PURCHASE_DETAIL_ID")
	private Details detail;

	@Column(name = "MODULER_NME")
	private String name;

	@Column(name = "MODULER_SERIAL")
	private String serial;
}