package com.sys.ims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CheckListDto {
    private String id;
    private List<CheckListListDto> list;
    private String type;
    private String productType;
    private String checkListCategoryId;
    private String categoryId;
    private String activeInd;
    private String updatedBy;
    private String createdBy;
    private String companyId;
}
