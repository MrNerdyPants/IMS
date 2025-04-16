package com.sys.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDto{
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiry;
    private String customer;
    private String period;
    private List<Detail> detail;

    private String companyId;
    private String updatedBy;
    private String createdBy;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private String id;
        private String type;
        private String paymentTerm;
        private String noOfVisits;
        private String amount;
    }
}
