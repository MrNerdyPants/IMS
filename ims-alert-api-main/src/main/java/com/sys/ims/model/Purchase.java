package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "IMS_PURCHASE")
@Builder
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PURCHASE_ID")
	@SequenceGenerator(name = "SEQ_PURCHASE_ID", sequenceName = "SEQ_PURCHASE_ID", allocationSize = 1)
	@Column(name = "PURCHASE_ID", nullable = false)
	private int id;

	@Column(name = "PURCHASE_DTE")
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "VENDOR_ID") // Foreign key in Purchase table
	private Vendor vendor;

	@ManyToOne
	@JoinColumn(name = "MANUFACTURER_ID")
	private Manufacturer manufacturer;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updateBy;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	// One purchase can have multiple details
	@OneToMany(mappedBy = "purchase",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Details> detail = new ArrayList<>();
}
