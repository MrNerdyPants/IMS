package com.sys.ims.controller;

import com.sys.ims.dto.CompanyDto;
import com.sys.ims.dto.EmployeeDto;
import com.sys.ims.dto.GroupDto;
import com.sys.ims.dto.GroupRightDto;
import com.sys.ims.dto.GroupRightFilterDto;
import com.sys.ims.dto.RightDto;
import com.sys.ims.dto.SiteDto;
import com.sys.ims.model.Company;
import com.sys.ims.model.Employee;
import com.sys.ims.model.Group;
import com.sys.ims.model.GroupRight;
import com.sys.ims.model.Right;
import com.sys.ims.model.Site;
import com.sys.ims.service.RightService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/right")
@RequiredArgsConstructor
public class RightController {

    @Autowired
    private RightService rightService;
    @Autowired
    private Utility utility;

    @PostMapping("/save-right")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveRight(@RequestBody RightDto rightDto) {
        ApiResponse apiResponse = rightService.saveRight(rightDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete-right/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteRight(@PathVariable(name = "id") String id) {
        Boolean flag = rightService.deleteRight(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @GetMapping("/get-right-child-nbr/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRightChildNbr(@PathVariable(name = "id") String id) {
        Integer nbr = rightService.getRightChildNbr(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", nbr), HttpStatus.OK);
    }

    @GetMapping("/get-parent-rights")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getParentRights() {
        List<Right> list = rightService.getParentRights();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-rights")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRights() {
        List<Right> list = rightService.getRights();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-group")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getGroups() {
        List<Group> list = rightService.getGroups();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @PostMapping("/save-group")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveGroup(@RequestBody GroupDto groupDto) {
        ApiResponse apiResponse = rightService.saveGroup(groupDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete-group/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteGroup(@PathVariable(name = "id") String id) {
        Boolean flag = rightService.deleteGroup(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-group-right")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveGroupRight(@RequestBody List<GroupRightDto> groupRightDto) {
        ApiResponse apiResponse = rightService.saveGroupRight(groupRightDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/get-group-rights")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getGroupRights(@RequestBody GroupRightFilterDto filterDto) {
        List<GroupRight> list = rightService.getGroupRights(filterDto.getGroupIds());
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @PostMapping("/save-company")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<?> saveCompany(@RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = rightService.saveCompany(companyDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-company")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCompanies() {
        List<Company> list = rightService.getCompanies();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-company/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCompanyById(@PathVariable String companyId) {
        Company list = rightService.getCompanyById(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-company/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") String id) {
        Boolean flag = rightService.deleteCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-employee")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto dto) {
        ApiResponse apiResponse = rightService.saveEmployee(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-employee")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getEmployee() {
        List<Employee> list = rightService.getEmployee();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-employee/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getEmployee(@PathVariable(name = "companyId") String companyId) {
        List<Employee> list = rightService.getEmployeeByCompany(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "id") String id) {
        Boolean flag = rightService.deleteEmployee(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-site")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<?> saveSite(@RequestBody SiteDto dto) {
        ApiResponse apiResponse = rightService.saveSite(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-site/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSites(@PathVariable(name = "companyId") String companyId) {
        List<Site> list = rightService.getSites(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-site/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteSite(@PathVariable(name = "id") String id) {
        Boolean flag = rightService.deleteSite(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }
}
