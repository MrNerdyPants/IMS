package com.sys.ims.controller;

import com.sys.ims.dto.AssignGroupDto;
import com.sys.ims.dto.AssignSiteDto;
import com.sys.ims.dto.UserDto;
import com.sys.ims.model.*;
import com.sys.ims.service.UserService;
import com.sys.ims.service.impl.UserDetailsImpl;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.JwtUtil;
import com.sys.ims.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    Utility utility;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            System.out.println(encoder.encode(authenticationRequest.getPassword()));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException bce) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(HttpStatus.FORBIDDEN.value(), "Invalid Credentials", null));
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        utility.addUserIfNotExist(userDetails.getUsername(), userDetails);
        // Find User Rights
        Set<Right> parentRight = new HashSet<>();
        // Arrange Rights and Childs
        parentRight = userDetails.getRights().stream().map(UserRight::getRight).filter(right -> right.getParent() != null).map(Right::getParent)
                .collect(Collectors.toSet());
        parentRight.forEach(pr -> {
            // Clear existing children to avoid duplicates
            pr.setChildren(new HashSet<>());
            // Find and add children from userRights
            Set<Right> children = userDetails.getRights().stream()
                    .map(UserRight::getRight) // Get the Right from each UserRight
                    .filter(right -> right != null && right.getParent() != null
                            && right.getParent().getId() == pr.getId()) // Filter children
                    .collect(Collectors.toSet()); // Collect the results into a Set
            // Add the filtered children to the parent's children set
            pr.setChildren(children);
        });
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().id(userDetails.getId())
                .fullName(userDetails.getFullName()).username(userDetails.getUsername()).token(jwtToken)
                .companyId(String.valueOf(userDetails.getCompanyId())).type(userDetails.getType())
                .email(userDetails.getEmail())
                .refId(userDetails.getRefId())
                .profilePicture(userDetails.getProfile())
                .rights(parentRight)
                .build();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", authenticationResponse), HttpStatus.OK);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtil.refreshToken(request);
        return ResponseEntity.ok(new AuthenticationResponse());
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> signUp(@RequestBody UserDto usersDto) {
        ApiResponse apiResponse = userService.saveUser(usersDto);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
    }

    @PostMapping("/save-user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.saveUser(userDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUser() {
        List<User> list = userService.getUser();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") String id) {
        Boolean flag = userService.deleteUser(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/assign-groups")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> assignGroups(@RequestBody List<AssignGroupDto> assignGroupDtos) {
        userService.assignGroup(assignGroupDtos);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", null), HttpStatus.OK);
    }

    @PostMapping("/assign-sites")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> assignSites(@RequestBody List<AssignSiteDto> dto) {
        userService.assignSite(dto);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", null), HttpStatus.OK);
    }

}
