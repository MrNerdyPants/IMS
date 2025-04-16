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

import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "IMS_NOTIFICATION")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTIFICATION_ID")
    @SequenceGenerator(name = "SEQ_NOTIFICATION_ID", sequenceName = "SEQ_NOTIFICATION_ID", allocationSize = 1)
    @Column(name = "NOTIFICATION_ID", nullable = false)
    private int id;

    @Column(name = "NOTIFICATION_MSG")
    private String message;

    @Column(name = "NOTIFICATION_DATE")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "CREATED_FOR")
    private User createdFor;
    
    @Column(name = "NOTIFICATION_STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;
    
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy;
    
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
