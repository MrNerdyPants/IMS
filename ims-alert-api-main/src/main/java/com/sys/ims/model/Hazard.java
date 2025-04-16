package com.sys.ims.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "IMS_HAZARD")
public class Hazard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HAZARD_ID")
    @SequenceGenerator(name = "SEQ_HAZARD_ID", sequenceName = "SEQ_HAZARD_ID", allocationSize = 1)
    @Column(name = "HAZARD_ID", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "HAZARD_CDE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "HAZARD_TYPE_ID")
    private HazardType type;

    @Column(name = "HAZARD_DESC")
    private String description;

    @OneToMany(mappedBy = "hazard", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Symtom> symtoms = new ArrayList<>();

    @OneToMany(mappedBy = "hazard", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Corrective> correctives = new ArrayList<>();
}
