package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "IMS_HELP")
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HELP_ID")
    @SequenceGenerator(name = "SEQ_HELP_ID", sequenceName = "SEQ_HELP_ID", allocationSize = 1)
    @Column(name = "HELP_ID", nullable = false)
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "HELP_DESC")
    private String description;

    @Column(name = "HELP_ATTACHMENT")
    private String attachment;
}
