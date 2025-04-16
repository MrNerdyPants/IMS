package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "IMS_MAINTENANCE")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAINTENANCE_ID")
    @SequenceGenerator(name = "SEQ_MAINTENANCE_ID", sequenceName = "SEQ_MAINTENANCE_ID", allocationSize = 1)
    @Column(name = "MAINTENANCE_ID", nullable = false)
    private int id;

    @Column(name = "MAINTENANCE_DTE")
    private String date;

    @Column(name = "COMPLAINT_NO")
    private String complaintNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTRACT_ID")
    private Contract contract;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPLAINT_SEND_TO")
    private User complaintSendTo;
    @Column(name = "MAINTENENCE_STATUS")
    private String status;
    @Column(name = "CHECK_IN")
    private String checkIn;
    @Column(name = "CHECK_OUT")
    private String checkOut;
    
    // @JsonFormat(pattern = "dd-MMM-yyyy")
    @Column(name = "VISIT_FROM")
    private String visitFrom;


    // @JsonFormat(pattern = "dd-MMM-yyyy")
    @Column(name = "VISIT_TO")
    private String visitTo;
    @Column(name = "HOLD_REASON")
    private String holdReason;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
    @Column(name = "PART_REPLACED")
    private String partReplaced;
    @Column(name = "VIDEO_ATTACHED")
    private String videoAttached;
    @Column(name = "PART_TITLE")
    private String partTitle;
    @Column(name = "PART_DTE")
    private String partString;
    @Column(name = "PART_LIFE")
    private String partLife;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "REMARK")
    private String remarks;
    @Column(name = "NO_OF_VISITS")
    private String noOfVisits;
    @Column(name = "MAINTENANCE_LVL")
    private String level;
    @Column(name = "MAINTENANCE_KNOWLEDGE")
    private String knowledge;
    @Column(name = "MAINTENANCE_CONVEYED")
    private String conveyed;

    @OneToMany(mappedBy = "maintenace", cascade = CascadeType.ALL)
    private List<MaintenanceDetail> detail = new ArrayList<>();
    @OneToMany(mappedBy = "maintenaceId")
    private List<MaintenanceClaim> claim = new ArrayList<>();
    @OneToMany(mappedBy = "maintenaceId")
    private List<MaintenanceParameter> parameters = new ArrayList<>();
    @OneToMany(mappedBy = "maintenaceId")
    private List<MaintenancePart> parts = new ArrayList<>();
    @OneToMany(mappedBy = "maintenaceId")
    private List<MaintenanceStaffCheckList> staffCheckList = new ArrayList<>();
    // @OneToMany(mappedBy = "maintenaceId")
    // @ElementCollection
    // private List<String> users;

}
