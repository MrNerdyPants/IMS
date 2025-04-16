package com.sys.ims.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
@Table(name = "IMS_CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORY_ID")
    @SequenceGenerator(name = "SEQ_CATEGORY_ID", sequenceName = "SEQ_CATEGORY_ID", allocationSize = 1)
    @Column(name = "CATEGORY_ID", nullable = false)
    private int id;

    @Column(name = "CATEGORY_NAME")
    private String name;

    @Column(name = "CATEGORY_CODE")
    private String code;

    @Column(name = "CATEGORY_ACTIVE_IND")
    private String activeInd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REF_CATEGORY_ID") // Foreign key for parent category
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true, fetch = FetchType.EAGER) // Manage child categories
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE,CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.EAGER)            
    private List<ProductType> types = new ArrayList<>();
}