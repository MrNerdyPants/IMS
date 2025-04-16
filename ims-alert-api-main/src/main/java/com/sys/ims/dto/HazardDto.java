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
public class HazardDto {
    private String id;
    private String categoryId;
    private String code;
    private String type;
    private String description;
    private List<HazardSymtomDto> symtoms;
    private List<HazardCorrectiveDto> correctives;
}
