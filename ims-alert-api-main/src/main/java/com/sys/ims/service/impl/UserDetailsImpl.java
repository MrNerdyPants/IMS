package com.sys.ims.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sys.ims.model.Right;
import com.sys.ims.model.User;
import com.sys.ims.model.UserRight;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private String id;

    private String token;

    private String username;
    private String type;
    private String email;
    private String refId;
    private String fullName;
    private String profile;
    private int companyId;
    @JsonIgnore
    private String password;

    private Set<UserRight> rights = new HashSet<>();

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            String id, String username, String password,
            Collection<? extends GrantedAuthority> authorities,Set<UserRight> rights,int companyId,
            String profile, String fullName,String type,String refId,String email
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
}
