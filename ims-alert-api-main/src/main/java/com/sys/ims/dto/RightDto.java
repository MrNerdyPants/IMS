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
public class RightDto {

    private String id;
    private String name;
    private String url;
    private String parentId;
    private Integer sort;
    private String activeInd;
    private String displayInd;
}
