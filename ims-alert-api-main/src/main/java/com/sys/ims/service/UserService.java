package com.sys.ims.service;

import com.sys.ims.dto.AssignGroupDto;
import com.sys.ims.dto.AssignSiteDto;
import com.sys.ims.dto.PasswordDto;
import com.sys.ims.dto.UserDto;
import com.sys.ims.dto.UserSaveDto;
import com.sys.ims.model.User;
import com.sys.ims.model.UserGroup;
import com.sys.ims.util.ApiResponse;

import java.util.List;

public interface UserService {

	ApiResponse saveUser(UserDto userDto);

	boolean usernameExist(UserDto userDto, User users, boolean isNewEntry);

	List<User> getUser();

	Boolean deleteUser(String id);

	ApiResponse assignGroup(List<AssignGroupDto> assignGroupDtos);

	ApiResponse assignSite(List<AssignSiteDto> dto);

	List<String> getGroupIdsAsString(List<UserGroup> userGroups);

}
