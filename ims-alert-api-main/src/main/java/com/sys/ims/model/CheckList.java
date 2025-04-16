package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "IMS_CHECKLIST")
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKLIST_ID")
    @SequenceGenerator(name = "SEQ_CHECKLIST_ID", sequenceName = "SEQ_CHECKLIST_ID", allocationSize = 1)
    @Column(name = "CHECKLIST_ID", nullable = false)
    private int id;

    @OneToMany(mappedBy = "checkList", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    private List<CheckListList> list = new ArrayList<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "CHECKLIST_TYPE_ID")
    private CheckListType type;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "CHECKLIST_CATEGORY_ID")
    private CheckListCategory checkListCategory;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "ISSUE_TYPE_ID")
    private IssueType issueType;

    @Column(name = "ACTIVE_IND")
    private String activeInd;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "UPDATED_BY")
    private User updateBy;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
