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
public class EmployeeDto{
    private String id;
    private String name;
    private String department;
    private String designation;
    private String siteId;
    private String companyId;
    private String fileNme;
    private String fileSize;
    private String fileType;
    private String shortName;
    private String modelTitle;
    private String mobileNo;
    private List<String> phone;
    private String email;
    private String loginId;
    private String loginPassword;

    
    private String updatedBy;
    private String createdBy;
}
