package com.sys.ims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerDto{
    
    private String id;
    private String name;
    private String shortName;
    private String countryId;
    private String stateId;
    private String cityId;
    private String siteId;
    private String address;
    private String officeAddress;
    private String mobileNo;
    private String landLine;
    private List<String> phone;
    private String authorizedPerson;
    private String email;
    private String ntnNumber;
    private String gstNumber;
    private String secpNumber;
//    private String serivceContract;
//    private String startDate;
//    private String endDate;
//    private String period;
//    private String amount;
    private String loginId;
    private String loginPassword;
    private String logo;
    private String fileNme;
    private String fileSize;
    private String fileType;
    private List<String> category;

    private String updatedBy;
    private String createdBy;
    private String companyId;
}
