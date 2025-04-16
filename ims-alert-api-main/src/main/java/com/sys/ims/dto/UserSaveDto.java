package com.sys.ims.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSaveDto {
	    private String id;
	    private String username;
	    private String name;
	    private String password;
	    private String profile;
	    private String type;
	    private String email;
	    private String refId;
	    private String companyId;
	    private String fileNme;
	    private String fileSize;
	    private String fileType;
//	    private List<String> groups;

}
