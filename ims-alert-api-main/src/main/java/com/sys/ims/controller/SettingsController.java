package com.sys.ims.controller;

import com.sys.ims.dto.*;
import com.sys.ims.model.*;
import com.sys.ims.service.SettingsService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private Utility utility;

    @PostMapping("/save-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = settingsService.saveCategory(categoryDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCategory() {
        List<Category> list = settingsService.getAllCategory();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCategory() {
        List<Category> list = settingsService.getCategory();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-category-by-company/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCategory(@PathVariable(name = "id") String id) {
        List<Category> list = settingsService.getCategoryByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-sub-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSubCategory() {
        List<Category> list = settingsService.getSubCategory();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-sub-category-by-parent/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSubCategoryByParent(@PathVariable(name = "id") String id) {
        List<Category> list = settingsService.getSubCategoryByParent(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @PostMapping("save-category-product-types")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCategoryProductType(@RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = settingsService.saveCategoryProductType(categoryDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    
    @PostMapping("/save-productType")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveProductType(@RequestBody ProductTypeDto productTypeDto) {
        ApiResponse apiResponse = settingsService.saveProductType(productTypeDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete-category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCategory(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    
    @DeleteMapping("/delete-product-type/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteProductyType(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteProductType(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-issue-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveIssueType(@RequestBody IssueTypeDto dto) {
        ApiResponse apiResponse = settingsService.saveIssueType(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-issue-type/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllIssueType(@PathVariable(name = "companyId") String companyId) {
        List<IssueType> list = settingsService.getAllIssueType(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-issue-type/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteIssueType(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteIssueType(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @GetMapping("/get-activity/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getActivity(@PathVariable(name = "companyId") String companyId) {
        List<Activity> list = settingsService.getActivity(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @PostMapping("/save-feature")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveFeature(@RequestBody FeatureDto featureDto) {
        ApiResponse apiResponse = settingsService.saveFeature(featureDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-feature")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFeature() {
        List<Feature> list = settingsService.getFeature();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-icon-library")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getIconLibrary() {
        List<IconLibrary> list = settingsService.getIconLibrary();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-feature-by-sub-category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFeatureBySubCategory(@PathVariable(name = "id") String id) {
        List<Feature> list = settingsService.getFeatureBySubCategory(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-feature/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteFeature(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteFeature(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-unit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveUnit(@RequestBody UnitDto dto) {
        ApiResponse apiResponse = settingsService.saveUnit(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-unit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUnit() {
        List<Unit> list = settingsService.getUnit();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-unit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteUnit(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteUnit(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    
    @PostMapping("/save-hazard-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveHazardType(@RequestBody HazardTypeDto dto) {
        ApiResponse apiResponse = settingsService.saveHazardType(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-hazard-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getHazardType() {
        List<HazardType> list = settingsService.getHazardType();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-hazard-type/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteHazardType(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteHazardType(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-hazard")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveHazard(@RequestBody HazardDto dto) {
        ApiResponse apiResponse = settingsService.saveHazard(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-hazard")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getHazard() {
        List<Hazard> list = settingsService.getHazard();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-hazard/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteHazard(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteHazard(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    
    @DeleteMapping("/delete-symtom/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteSymtoms(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteSymtoms(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    
    @DeleteMapping("/delete-corrective/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCorrectives(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCorrectives(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-country")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCountry(@RequestBody CountryDto dto) {
        ApiResponse apiResponse = settingsService.saveCountry(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-country")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCountry() {
        List<Country> list = settingsService.getCountry();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-country/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCountry(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCountry(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-state")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveState(@RequestBody StateDto dto) {
        ApiResponse apiResponse = settingsService.saveState(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-state")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getState() {
        List<State> list = settingsService.getState();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-state-by-country/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getStateByCountry(@PathVariable(name = "id") String id) {
        List<State> list = settingsService.getStateByCountry(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-state/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteState(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteState(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-city")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCity(@RequestBody CityDto dto) {
        ApiResponse apiResponse = settingsService.saveCity(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-city")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCity() {
        List<City> list = settingsService.getCity();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-city-by-state/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCityByState(@PathVariable(name = "id") String id) {
        List<City> list = settingsService.getCityByState(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-city-by-country/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCityByCountry(@PathVariable(name = "id") String id) {
        List<City> list = settingsService.getCityByCountry(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-city/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCity(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCity(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-manufacturer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveManufacturer(@RequestBody ManufacturerDto dto) {
        ApiResponse apiResponse = settingsService.saveManufacturer(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-manufacturer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getManufacturer() {
        List<Manufacturer> list = settingsService.getManufacturer();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-manufacturer/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "companyId") String id) {
        List<Manufacturer> list = settingsService.getManufacturerByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-manufacturer-by-category/{id}/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getManufacturerByCategory(@PathVariable(name = "id") String id,
            @PathVariable(name = "companyId") String companyId) {
        List<Manufacturer> list = settingsService.getManufacturerByCategory(id, companyId);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-manufacturer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteManufacturer(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteManufacturer(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-customer")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto dto) {
        ApiResponse apiResponse = settingsService.saveCustomer(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-customer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCustomer() {
        List<Customer> list = settingsService.getCustomer();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-customer/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCustomer(@PathVariable(name = "companyId") String id) {
        List<Customer> list = settingsService.getCustomerByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCustomer(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCustomer(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-vendor")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<?> saveVendor(@RequestBody VendorDto dto) {
        ApiResponse apiResponse = settingsService.saveVendor(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-vendor")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getVendor() {
        List<Vendor> list = settingsService.getVendor();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @GetMapping("/get-vendor/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getVendor(@PathVariable(name = "companyId") String id) {
        List<Vendor> list = settingsService.getVendorByCompany(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-vendor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteVendor(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteVendor(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-department")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveDepartment(@RequestBody DepartmentDto dto) {                
        ApiResponse apiResponse = settingsService.saveDepartment(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    @GetMapping("/get-department")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getDepartment() {
        List<Department> list = settingsService.getDepartment();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-department/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteDepartment(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-designation")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveDesignation(@RequestBody DesignationDto dto) {                
        ApiResponse apiResponse = settingsService.saveDesignation(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-designation")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getDesignation() {
        List<Designation> list = settingsService.getDesignation();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-designation/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteDesignation(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteDesignation(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    
    @PostMapping("/save-checklist-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCheckListType(@RequestBody CheckListTypeDto dto) {
        ApiResponse apiResponse = settingsService.saveCheckListType(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-checklist-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCheckListType() {
        List<CheckListType> list = settingsService.getCheckListType();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-checklist-type/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCheckListType(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCheckListType(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-checklist-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCheckListCategory(@RequestBody CheckListCategoryDto dto) {
        ApiResponse apiResponse = settingsService.saveCheckListCategory(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-checklist-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCheckListCategory() {
        List<CheckListCategory> list = settingsService.getCheckListCategory();
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", list), HttpStatus.OK);
    }

    @DeleteMapping("/delete-checklist-category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCheckListCategory(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCheckListCategory(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-customer-check-list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveCheckList(@RequestBody CheckListDto dto) {
        ApiResponse apiResponse = settingsService.saveCheckList(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-check-list-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCheckListCategory() {
        List<CheckListCategory> list = settingsService.getAllCheckListCategory();
        return new ResponseEntity<>(utility.buildApiResponse(list.size() > 0 ? 200 : 404, "Success", list),
                HttpStatus.OK);
    }

    @GetMapping("/get-staff-check-list/{type}/{category}/{categoryId}/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllStaffCheckList(@PathVariable(name = "type") String type,
            @PathVariable(name = "category") String category, @PathVariable(name = "categoryId") String categoryId,
            @PathVariable(name = "companyId") String companyId) {
        List<CheckList> list = settingsService.getAllStaffCheckList(type, category, categoryId, companyId);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() > 0 ? 200 : 404, "Success", list),
                HttpStatus.OK);
    }

    @GetMapping("/get-customer-check-list/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCheckList(@PathVariable(name = "companyId") String companyId) {
        List<CheckList> list = settingsService.getAllCheckList(companyId);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() > 0 ? 200 : 404, "Success", list),
                HttpStatus.OK);
    }

    @GetMapping("/get-check-list-by-type/{type}/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllCheckList(@PathVariable(name = "type") String type,
            @PathVariable(name = "companyId") String companyId) {
        List<CheckList> list = settingsService.getAllCheckList(type, companyId);
        return new ResponseEntity<>(utility.buildApiResponse(list.size() > 0 ? 200 : 404, "Success", list),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete-customer-check-list/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCheckList(@PathVariable(name = "id") String id) {
        Boolean flag = settingsService.deleteCheckList(id);
        return new ResponseEntity<>(utility.buildApiResponse(200, "Success", flag), HttpStatus.OK);
    }

    @PostMapping("/save-help")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveHelp(@RequestBody HelpDto dto) {
        ApiResponse apiResponse = settingsService.saveHelp(dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
