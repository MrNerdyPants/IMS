package com.sys.ims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ManufacturerDto {
    
    private String id;
    private String name;
    private String shortName;
    private String countryId;
    private String cityId;
    private String address;
    private String mobileNo;
    private String landLine;
    private String webLink;
    private String introduce;
    private String logo;
    private String fileNme;
    private String fileSize;
    private String fileType;
    private List<String> product;
    
    private String updatedBy;
    private String createdBy;
    private String companyId;
}
