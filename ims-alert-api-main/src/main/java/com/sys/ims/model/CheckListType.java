package com.sys.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "IMS_CHECKLIST_TYPE")
public class CheckListType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKLIST_TYPE_ID")
    @SequenceGenerator(name = "SEQ_CHECKLIST_TYPE_ID", sequenceName = "SEQ_CHECKLIST_TYPE_ID", allocationSize = 1)
    @Column(name = "CHECKLIST_TYPE_ID", nullable = false)
    private int id;

    @Column(name = "TITLE")
    private String title;
}
