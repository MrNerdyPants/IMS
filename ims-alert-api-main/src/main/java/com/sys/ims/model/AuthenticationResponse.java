package com.sys.ims.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationResponse {
	private String id;
	private String fullName;
	private String username;
	private String token;
	private String companyId;
	private String type;
	private String email;
	private String refId;
	private String profilePicture;
	private Set<Right> rights;
}
