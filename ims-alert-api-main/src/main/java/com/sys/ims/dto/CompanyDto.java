package com.sys.ims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyDto {
    private String id;
    private String name;
    private String countryId;
    private String stateId;
    private String cityId;
    private String address;
    private String officeAddress;
    private String landLine;
    private String authorizedPerson;
    private String email;
    private String ntnNumber;
    private String gstNumber;
    private String secpNumber;
    private String loginId;
    private String loginPassword;
    private String logo;
    private String fileNme;
    private String fileSize;
    private String fileType;
    private List<String> phone;
    private List<String> category;
}
