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
public class MaintenanceDto {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String customer;
    private String employee;
    private String product;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitFrom;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitTo;
    
    private String companyId;
    private String createdBy;
    private String updatedBy;

    private String complaintSendTo;
    
    private List<Detail> detail;
    private List<Detail> parameters;
    private List<Detail> parts;

    private String travel;
    private String fuel;
    private String entertainment;
    private String hotelling;
    private String other;

    private String partReplaced;
    private String videoAttached;
    private String partTitle;
    private String partDate;
    private String partLife;
    private String amount;
    private String remarks;
    private List<Detail> staffCheckList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private String id;
        private String product;
        private String serial;
        private String checklistId;
        private String title;
        private String type;
        private String value;
        private String stdValue;
        private String minRange;
        private String maxRange;
        private List<String> checkList;
    }
}
