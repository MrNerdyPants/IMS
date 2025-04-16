package com.sys.ims.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String category;
    private String subCategory;
    private String manufacturer;
    private String productType;
    // private List<String> features;
    private String description;
    private String functionDetail;
    private String modelTitle;
    private String barCode;
    private String warranty;
    private List<String> serials;
    private List<Muduler> mudulers;
    private List<Part> parts;
    private List<Parameter> parameters;
    private List<Video> videoLinks;
    private List<Attachment> documents;

    private String companyId;
    private String createdBy;
    private String updatedBy;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Muduler {
        private String id;
        private String title;
        private String serial;
        private String barCode;
        private String warranty;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Part {
        private String id;
        private String title;
        private String price;
        private String life;
        private String unit;
        private String period;
        private String date;
        private String value;
        private String reading;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Parameter {
        private String id;
        private String title;
        private String description;
        private String standerdValue;
        private String minRange;
        private String maxRange;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attachment {
        private String id;
        private String fileData;
        private String fileNme;
        private String fileSize;
        private String documentDesc;
        private String contentType;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Video {
        private String id;
        private String videoLink;
    }
}
