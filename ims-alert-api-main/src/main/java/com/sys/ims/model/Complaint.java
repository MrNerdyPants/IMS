package com.sys.ims.model;

import com.sys.ims.enums.ComplaintPriority;
import com.sys.ims.enums.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private NCustomer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ComplaintPriority priority;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Employee assignedTo;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
