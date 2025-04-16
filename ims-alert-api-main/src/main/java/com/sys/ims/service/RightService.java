package com.sys.ims.service;

import com.sys.ims.dto.*;
import com.sys.ims.model.*;
import com.sys.ims.util.ApiResponse;

import java.util.List;

public interface RightService {

    ApiResponse saveRight(RightDto rightDto);

    ApiResponse saveGroup(GroupDto groupDto);

    ApiResponse saveGroupRight(List<GroupRightDto> groupRightDto);

    List<Group> getGroups();

    List<Right> getRights();

    List<GroupRight> getGroupRights(List<String> groupsId);

    List<Right> getParentRights();

    Integer getRightChildNbr(String id);

    Boolean deleteGroup(String id);

    Boolean deleteRight(String id);

    ApiResponse saveCompany(CompanyDto companyDto);

    List<Company> getCompanies();

    Boolean deleteCompany(String id);

    ApiResponse saveEmployee(EmployeeDto dto);

    List<Employee> getEmployee();

    List<Employee> getEmployeeByCompany(String id);

    Boolean deleteEmployee(String id);

    ApiResponse saveSite(SiteDto dto);

    List<Site> getSites(String companyId);

    Boolean deleteSite(String id);

    Company getCompanyById(String companyId);

}
