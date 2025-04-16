package com.sys.ims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sys.ims.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    private String id;
    private String username;
    private String name;
    private String password;
    private String profile; // Image in base64
    private String type;
    private String email;
    private String refId; // Not cleared
    private String companyId;
    private String fileNme;
    private String fileSize;
    private String fileType;
    private String shortName;
    private String modelTitle;
    private String mobileNo;
    private List<String> groups;
}
