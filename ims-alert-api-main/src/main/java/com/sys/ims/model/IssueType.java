package com.sys.ims.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "IMS_ISSUE_TYPE")
public class IssueType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ISSUE_TYPE_ID")
    @SequenceGenerator(name = "SEQ_ISSUE_TYPE_ID", sequenceName = "SEQ_ISSUE_TYPE_ID", allocationSize = 1)
    @Column(name = "ISSUE_TYPE_ID", nullable = false)
    private int id;

    @Column(name = "ISSUE_NME")
    private String name;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updateBy;

    // @JsonIgnore
    // @ManyToOne
    // @JoinColumn(name = "COMPANY_ID")
    // private Company company;
}
