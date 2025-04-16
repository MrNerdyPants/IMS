package com.sys.ims.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "IMS_PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_ID")
	@SequenceGenerator(name = "SEQ_PRODUCT_ID", sequenceName = "SEQ_PRODUCT_ID", allocationSize = 1)
	@Column(name = "PRODUCT_ID")
	private int id;

	@Column(name = "PRODUCT_NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "SUB_CATEGORY_ID")
	private Category subCategory;

	@ManyToOne
	@JoinColumn(name = "MANUFACTURER_ID")
	private Manufacturer manufacturer;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_TYPE_ID")
	private ProductType productType;

	// @ElementCollection
	// private List<Feature> features = new ArrayList<>();
	@Column(name = "PROD_DESC")
	private String description;
	@Column(name = "FUNCTIONAL_DETAIL")
	private String functionDetail;

	@Column(name = "MODEL_TITLE")
	private String modelTitle;
	@Column(name = "BARCODE")
	private String barCode;
	@Column(name = "WARRENTY")
	private String warranty;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updateBy;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	@OneToMany(mappedBy = "product", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ProductSerial> serials = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ProductModuler> mudulers = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ProductPart> parts = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ProductParam> parameters = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ProductVideo> videoLinks = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
	private List<ProductAttachment> documents = new ArrayList<>();
}
