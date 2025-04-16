package com.sys.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "IMS_CHECKLIST_LIST")
public class CheckListList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKLIST_LIST_ID")
    @SequenceGenerator(name = "SEQ_CHECKLIST_LIST_ID", sequenceName = "SEQ_CHECKLIST_LIST_ID", allocationSize = 1)
    @Column(name = "CHECKLIST_LIST_ID", nullable = false)
    private int id;

    @JsonIgnore
    @JoinColumn(name = "CHECKLIST_ID")
    @ManyToOne
    private CheckList checkList;

    @Column(name = "TITLE")
    private String title;
}
