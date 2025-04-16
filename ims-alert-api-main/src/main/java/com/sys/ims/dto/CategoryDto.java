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
public class CategoryDto {
    private String id;
    private String name;
    private String code;
    private String activeInd;
    private String parentId;
    private List<ProductTypeDto> types;
}
