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
public class SalesDto{
    private String id;
    

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String customer;
    private List<Detail> detail;
    
    private String updatedBy;
    private String createdBy;
    private String companyId;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private String id;
        private String productType;
        private String product;
        private String model;
        private String serial;
        private String referenceNo;
        private List<Muduler> mudulerSerial;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Muduler {
            private String id;
            private String name;
            private String serial;
        }
    }
}
