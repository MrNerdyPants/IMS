package com.sys.ims.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="IMS_ACTIVITY")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACTIVITY_ID")
	@SequenceGenerator(name = "SEQ_ACTIVITY_ID", sequenceName = "SEQ_ACTIVITY_ID", allocationSize = 1)
	@Column(name = "ACTIVITY_ID", nullable = false)
    private int id;
    
    @Column(name = "ACTIVITY_DESC")
	private String description;
    
    @Column(name = "ACTION_BY")
	private String actionBy;
    
    @ManyToOne
    @JoinColumn(name = "PREPARED_BY")
	private User preparedBy;
    
    @Column(name = "ACTIVITY_PROFILE")
	private String profile;
    
    @Column(name = "PREPARED_DTE")
	private Date createdAt;
    
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
	private Company companyId;
}
