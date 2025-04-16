package com.sys.ims.service;

import com.sys.ims.dto.*;
import com.sys.ims.model.*;
import com.sys.ims.util.ApiResponse;

import java.util.List;

public interface SettingsService {

    ApiResponse saveCategory(CategoryDto dto);
    List<Category> getSubCategory();
    List<Category> getAllCategory();
    List<Category> getCategory();
    List<Category> getCategoryByCompany(String companyId);
    List<Category> getSubCategoryByParent(String id);
    Boolean deleteCategory(String id);
    ApiResponse saveIssueType(IssueTypeDto dto);
    List<IssueType> getAllIssueType(String companyId);
    Boolean deleteIssueType(String id);
    List<Activity> getActivity(String companyId);
    ApiResponse saveFeature(FeatureDto dto);
    List<Feature> getFeature();
    List<IconLibrary> getIconLibrary();
    List<Feature> getFeatureBySubCategory(String id);
    Boolean deleteFeature(String id);
    ApiResponse saveUnit(UnitDto dto);
    List<Unit> getUnit();
    Boolean deleteUnit(String id);

    
    ApiResponse saveHazardType(HazardTypeDto dto);
    List<HazardType> getHazardType();
    Boolean deleteHazardType(String id);
    ApiResponse saveHazard(HazardDto dto);
    List<Hazard> getHazard();
    Boolean deleteHazard(String id);
    ApiResponse saveCountry(CountryDto dto);
    List<Country> getCountry();
    Boolean deleteCountry(String id);
    
    ApiResponse saveState(StateDto dto);
    List<State> getState();
    List<State> getStateByCountry(String id);
    Boolean deleteState(String id);
    ApiResponse saveCity(CityDto dto);
    List<City> getCity();
    List<City> getCityByState(String id);
    List<City> getCityByCountry(String id);
    Boolean deleteCity(String id);
    ApiResponse saveManufacturer(ManufacturerDto dto);
    List<Manufacturer> getManufacturer();
    List<Manufacturer> getManufacturerByCompany(String id);
    List<Manufacturer> getManufacturerByCategory(String id,String companyId);
    Boolean deleteManufacturer(String id);
    ApiResponse saveCustomer(CustomerDto dto);
    List<Customer> getCustomer();
    List<Customer> getCustomerByCompany(String id);
    Boolean deleteCustomer(String id);
    ApiResponse saveVendor(VendorDto dto);
    List<Vendor> getVendor();
    List<Vendor> getVendorByCompany(String id);
    Boolean deleteVendor(String id);
    
    ApiResponse saveDepartment(DepartmentDto dto);
    List<Department> getDepartment();
    Boolean deleteDepartment(String id);
    
    ApiResponse saveDesignation(DesignationDto dto);
    List<Designation> getDesignation();
    Boolean deleteDesignation(String id);

    ApiResponse saveCheckListType(CheckListTypeDto dto);
    List<CheckListType> getCheckListType();
    Boolean deleteCheckListType(String id);
    
    ApiResponse saveCheckListCategory(CheckListCategoryDto dto);
    List<CheckListCategory> getCheckListCategory();
    Boolean deleteCheckListCategory(String id);

    ApiResponse saveCheckList(CheckListDto dto);
    List<CheckList> getAllCheckList(String companyId);
    List<CheckList> getAllCheckList(String type,String companyId);
    List<CheckListCategory> getAllCheckListCategory();
    Boolean deleteCheckList(String id);
    ApiResponse saveHelp(HelpDto dto);
    List<CheckList> getAllStaffCheckList(String type,String category,String categoryId,String companyId);

    
    ApiResponse saveCategoryProductType(CategoryDto dto);
    ApiResponse saveProductType(ProductTypeDto dto);
    Boolean deleteProductType(String producTypeId);
    Boolean deleteSymtoms(String symtomId);
    Boolean deleteCorrectives(String correctiveId);
}
