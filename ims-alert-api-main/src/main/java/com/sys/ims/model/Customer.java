package com.sys.ims.model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "IMS_CUSTOMER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CUSTOMER_ID")
    @SequenceGenerator(name = "SEQ_CUSTOMER_ID", sequenceName = "SEQ_CUSTOMER_ID", allocationSize = 1)
	@Column(name = "CUSTOMER_ID")
	private int id;
	@Column(name="CUSTOMER_NME")
	private String name;
	@Column(name="CUSTOMER_SHORT_NME")
	private String shortName;
	@OneToOne
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;
	@OneToOne
	@JoinColumn(name = "PROVINCE_ID")
	private State state;
	@OneToOne
	@JoinColumn(name = "CITY_ID")
	private City city;
	@OneToOne
	@JoinColumn(name = "SITE_ID")
	private Site site;
	@Column(name="ADDRESS")
	private String address;
	@Column(name="OFFICE_ADDRESS")
	private String officeAddress;
	@Column(name="MOBILE_NO")
	private String mobileNo;
	@Column(name="LAND_LINE")
	private String landLine;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL
// 	, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
	)
	private List<CustomerContact> phone = new ArrayList<>();
	@Column(name="AUTH_PERSON")
	private String authorizedPerson;
	@Column(name="EMAIL")
	private String email;
	@Column(name="NTN_NBR")
	private String ntnNumber;
	@Column(name="GST_NBR")
	private String gstNumber;
	@Column(name="SCEP_NBR")
	private String secpNumber;
//    private String serivceContract;
//    private String startDate;
//    private String endDate;
//    private String period;
//    private String amount;
	@Column(name="LOGIN_ID")
	private String loginId;
	@Column(name="LOGIN_PASSWORD")
	private String loginPassword;
	@Lob
	@Column(name="LOGO")
	private byte[] logo;
	@Column(name="FILE_NME")
	private String fileNme;
	@Column(name="FILE_SIZE")
	private String fileSize;
	@Column(name="FILE_TYPE")
	private String fileType;
	
	@OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL
	// , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
	)
	private List<CustomerCategory> category = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name="PREPARED_BY")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private User updateBy;
	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private Company companyId;
}
