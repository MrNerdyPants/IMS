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
public class SiteDto{
    private String id;
    private String name;
    private String countryId;
    private String stateId;
    private String cityId;
    private String address;
    private String companyId;
    private String updatedBy;
    private String createdBy;
    private List<String> phone;
}
