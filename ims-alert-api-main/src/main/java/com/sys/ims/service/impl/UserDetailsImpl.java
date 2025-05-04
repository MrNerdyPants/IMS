package com.sys.ims.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sys.ims.enums.UserStatus;
import com.sys.ims.enums.UserType;
import com.sys.ims.model.NUser;
import com.sys.ims.model.Permission;
import com.sys.ims.model.User;
import com.sys.ims.model.UserRight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String id;

    private String token;

    private String username;
    private String type;

    private String refId;

    private String profile;
    private int companyId;

    private Set<UserRight> rights = new HashSet<>();

//    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            String id, String username, String password,
            Collection<? extends GrantedAuthority> authorities, Set<UserRight> rights, int companyId,
            String profile, String fullName, String type, String refId, String email
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.rights = rights;
        this.companyId = companyId;
        this.profile = profile;
        this.fullName = fullName;
        this.type = type;
        this.refId = refId;
        this.email = email;
        // this.setRights(rights);
    }

    public UserDetailsImpl(
            String id, String username, String password, String fullName
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getGroups().stream()
                .map(role -> new SimpleGrantedAuthority(role.getGroup().getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                String.valueOf(user.getId()),
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getRights(),
                user.getCompany().getId(),
                user.getProfile(),
                user.getName(),
                user.getType(),
                user.getRefId(),
                user.getEmail()
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String getFullName() {
        return fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<UserRight> getRights() {
        return rights;
    }


    public void setRights(Set<UserRight> rights) {
        this.rights = rights;
    }

    public String getType() {
        return type;
    }

    public String getRefId() {
        return refId;
    }

    public String getEmail() {
        return email;
    }


    private UUID uuid;
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private UserType userType;
    private UserStatus status;

    public static UserDetailsImpl build(NUser user) {
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPasswordHash(),
                authorities,
                user.getUserType(),
                user.getStatus(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone()
        );
    }

    public UserDetailsImpl(
            UUID uuid,
            String email,
            String fullName,
            String passwordHash,
            Collection<? extends GrantedAuthority> authorities,
            UserType type,
            UserStatus userStatus
    ) {
        this.uuid = uuid;
        this.email = email;
        this.fullName = fullName;
        this.password = passwordHash;
        this.authorities = authorities;
        this.userType = type;
        this.status = userStatus;


    }

    public UserDetailsImpl(
            UUID uuid,
            String email,
            String fullName,
            String passwordHash,
            Collection<? extends GrantedAuthority> authorities,
            UserType type,
            UserStatus userStatus,
            String firstName,
            String lastName,
            String phoneNumber
    ) {
        this.uuid = uuid;
        this.email = email;
        this.fullName = fullName;
        this.password = passwordHash;
        this.authorities = authorities;
        this.userType = type;
        this.status = userStatus;
        this.firstName= firstName;
        this.lastName= lastName;
        this.phoneNumber = phoneNumber;


    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return status != UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}
