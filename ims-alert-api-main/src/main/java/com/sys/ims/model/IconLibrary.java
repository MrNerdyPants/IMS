package com.sys.ims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "icon_library")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IconLibrary {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String name;
}
