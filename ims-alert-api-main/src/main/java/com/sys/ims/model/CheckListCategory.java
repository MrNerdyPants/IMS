package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "IMS_CHECKLIST_CATEGORY")
public class CheckListCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKLIST_CATEGORY_ID")
    @SequenceGenerator(name = "SEQ_CHECKLIST_CATEGORY_ID", sequenceName = "SEQ_CHECKLIST_CATEGORY_ID", allocationSize = 1)
    @Column(name = "CHECKLIST_CATEGORY_ID", nullable = false)
    private int id;

    @Column(name = "TITLE")
    private String title;
}
