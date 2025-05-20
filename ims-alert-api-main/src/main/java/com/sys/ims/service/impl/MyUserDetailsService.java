package com.sys.ims.service.impl;

import com.sys.ims.dto.AssignGroupDto;
import com.sys.ims.dto.AssignSiteDto;
import com.sys.ims.dto.UserDto;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Company;
import com.sys.ims.model.Group;
import com.sys.ims.model.GroupRight;
import com.sys.ims.model.NUser;
import com.sys.ims.model.User;
import com.sys.ims.model.UserGroup;
import com.sys.ims.model.UserRight;
import com.sys.ims.repository.CompanyRepository;
import com.sys.ims.repository.GroupRepository;
import com.sys.ims.repository.GroupRightRepository;
import com.sys.ims.repository.NUserRepository;
import com.sys.ims.repository.RightRepository;
import com.sys.ims.repository.UserGroupRepository;
import com.sys.ims.repository.UserRepository;
import com.sys.ims.service.UserService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService, UserService {
    @Autowired
    private Utility utility;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private RightRepository rightRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupRightRepository groupRightRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Lazy
    PasswordEncoder passwordEncoder;

    //
    @Override
    public List<String> getGroupIdsAsString(List<UserGroup> userGroups) {
        return userGroups.stream().filter(userGroup -> userGroup.getGroup() != null)
                .map(userGroup -> userGroup.getGroup().getId()).map(String::valueOf).collect(Collectors.toList());
    }

//    @Override
//    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
//        User currentUser = usersRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found for the username: " + username));
//        return UserDetailsImpl.build(currentUser);
//    }

    private final NUserRepository nUserRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        NUser user = nUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return UserDetailsImpl.build(user);
    }

    @Override
    public ApiResponse assignGroup(List<AssignGroupDto> assignGroupDtos) {
        if (Utility.isNotNull(assignGroupDtos) && Utility.isNotNullAndEmpty(assignGroupDtos.get(0).getUserId())) {
            // Find User
            Optional<User> result = usersRepository.findById(Integer.parseInt(assignGroupDtos.get(0).getUserId()));
            if (result.isPresent()) {
                // Delete All User Rights
                // List<UserGroup> checkUserGroups =
                // userGroupRepository.findAllByUser(result.get());
                // if (!checkUserGroups.isEmpty()) {
                // for (UserGroup userGroup : checkUserGroups) {
                // System.out.println("UserGroup Id : " + userGroup.getId());
                // userGroupRepository.deleteById(userGroup.getId());
                // }
                // }
                List<Integer> groups = assignGroupDtos.stream()
                        .map(assignGroupDto -> Integer.parseInt(assignGroupDto.getGroupId()))
                        .collect(Collectors.toList());
                // Get Groups From Group Table
                List<UserGroup> assignedUserGroup = new ArrayList<>();
                List<Group> groupRecords = groupRepository.findAllByIdIn(groups);
                for (Group group : groupRecords) {
                    UserGroup userGroup = UserGroup.builder().user(result.get()).group(group).build();
                    assignedUserGroup.add(userGroup);
                    userGroupRepository.save(userGroup);
                }
                result.get().setGroups(assignedUserGroup);
                User user = result.get();
                return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
                        Utility.isNotNull(user) ? user.getId() : null);
            }
            return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
                    null);
        }
        return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND, null);
    }

    @Override
    public ApiResponse assignSite(List<AssignSiteDto> dto) {
        if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.get(0).getUserId())) {
            Optional<User> result = usersRepository.findById(Integer.parseInt(dto.get(0).getUserId()));
            if (result.isPresent()) {
                List<String> sites = dto.stream().map(c -> c.getSiteId()).collect(Collectors.toList());
                // result.get().setSites(sites);
                User user = usersRepository.save(result.get());
                return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
                        Utility.isNotNull(user) ? user.getId() : null);
            }
            return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
                    null);
        }
        return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND, null);
    }

    @Override
    public ApiResponse saveUser(UserDto usersDto) {
        User users = new User();
        if (Utility.isNotNull(usersDto) && Utility.isNotNullAndEmpty(usersDto.getId())) {
            Optional<User> result = usersRepository.findById(Integer.parseInt(usersDto.getId()));
            if (result.isPresent()) {
                if (usernameExist(usersDto, result.get(), false)) {
                    throw new BaseException.EmailExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
                }
                utility.copyNonNullProperties(usersDto, result.get());
                if (Utility.isNotNullAndEmpty(usersDto.getPassword())) {
                    result.get().setPassword(passwordEncoder.encode(usersDto.getPassword()));
                }

                // Assign User Rights
                if (usersDto.getGroups() != null && !usersDto.getGroups().isEmpty()) {
                    List<Group> groups = groupRepository.findAllByIdIn(usersDto.getGroups().stream()
                            .map(groupId -> Integer.parseInt(groupId)).collect(Collectors.toList()));

                    // Filter User Rights
                    List<GroupRight> grights = groupRightRepository.findByGroupIn(groups);

                    for (Group group : groups) {
                        UserGroup userGroupe = result.get().getGroups().stream()
                                .filter(ug -> ug.getGroup().getId() == group.getId()).findFirst()
                                .orElse(new UserGroup());
                        userGroupe.setGroup(group);
                        userGroupe.setUser(result.get());
                        result.get().getGroups().add(userGroupe);
                    }

                    for (GroupRight grs : grights) {
                        UserRight usrr = result.get().getRights().stream()
                                .filter(ur -> ur.getId() != 0 && grs.getRight().getId() == ur.getRight().getId())
                                .findFirst().orElse(new UserRight());
                        usrr.setRight(grs.getRight());
                        usrr.setUser(result.get());
                        result.get().getRights().add(usrr);
                    }

                } else {
                    result.get().setGroups(null);
                    result.get().setRights(null);
                }
                usersRepository.save(result.get());
                return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
            }
            return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
                    null);
        } else {
            if (usernameExist(usersDto, users, true)) {
                throw new BaseException.EmailExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
            }
            usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            utility.copyNonNullProperties(usersDto, users);
            Optional<Company> comp = companyRepository.findById(Integer.parseInt(usersDto.getCompanyId()));
            if (comp.isPresent()) {
                users.setCompany(comp.get());
            }

            // Assign User Rights
            if (usersDto.getGroups() != null && !usersDto.getGroups().isEmpty()) {
                List<Group> groups = groupRepository.findAllByIdIn(usersDto.getGroups().stream()
                        .map(groupId -> Integer.parseInt(groupId)).collect(Collectors.toList()));
                // Filter User Rights
                List<GroupRight> grights = groupRightRepository.findByGroupIn(groups);

                users.setGroups(new ArrayList<UserGroup>());
                for (Group group : groups) {
                    UserGroup userGroupe = users.getGroups().stream()
                            .filter(ug -> ug.getGroup().getId() != 0 && ug.getGroup().getId() == group.getId())
                            .findFirst().orElse(new UserGroup());
                    userGroupe.setGroup(group);
                    userGroupe.setUser(users);
                    users.getGroups().add(userGroupe);
                }

                users.setRights(new HashSet<>());
                for (GroupRight grs : grights) {
                    UserRight usrr = users.getRights().stream()
                            .filter(ur -> ur.getRight().getId() != 0 && grs.getRight().getId() == ur.getRight().getId())
                            .findFirst().orElse(new UserRight());
                    usrr.setRight(grs.getRight());
                    usrr.setUser(users);
                    users.getRights().add(usrr);
                }
            } else {
                users.setGroups(null);
                users.setRights(null);
            }

            users = usersRepository.save(users);
            return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
                    Utility.isNotNull(users) ? users.getId() : null);
        }
    }

    @Override
    public boolean usernameExist(UserDto usersDto, User users, boolean isNewEntry) {
        if (Utility.isNotNullAndEmpty(usersDto.getUsername())) {
            Optional<User> checkUsername = usersRepository.findByUsername(usersDto.getUsername());
            if (isNewEntry && checkUsername.isPresent()) {
                return true;
            } else
                return !isNewEntry && checkUsername == null && users.getId() == 0;
        }
        return false;
    }

    @Override
    public List<User> getUser() {
        return usersRepository.findAll();
    }

    @Override
    public Boolean deleteUser(String id) {
        usersRepository.deleteById(Integer.parseInt(id));
        Boolean flag = usersRepository.existsById(Integer.parseInt(id));
        return !flag;
    }

}
