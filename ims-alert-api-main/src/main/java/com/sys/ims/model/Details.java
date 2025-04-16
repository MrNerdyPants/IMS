package com.sys.ims.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

//Detail class as a separate entity
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "IMS_PURCHASE_DETAIL")
public class Details {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PURCHASE_DETAIL_ID")
	@SequenceGenerator(name = "SEQ_PURCHASE_DETAIL_ID", sequenceName = "SEQ_PURCHASE_DETAIL_ID", allocationSize = 1)
	@Column(name = "PURCHASE_DETAIL_ID", nullable = false)
	private int id; // Change to Long for better scalability

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PURCHASE_ID")
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_TYP")
	private ProductType productType;
	

	@ManyToOne
	@JoinColumn(name = "PRODUCT")
	private Product product;

	@Column(name = "PROD_MODEL")
	private String model;

	@Column(name = "SERIAL_NO")
	private String serial;

	@Column(name = "QTY")
	private String qty;
	// Many details can have multiple mudulerSerials
	@OneToMany(mappedBy = "detail", cascade = CascadeType.ALL)
	private List<Muduler> mudulerSerial = new ArrayList<>();
}