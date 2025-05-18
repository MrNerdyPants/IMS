import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbToastModule, NgbNavModule, NgbDropdownModule, NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { FlatpickrModule } from 'angularx-flatpickr';
import { CountToModule } from 'angular-count-to';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { SimplebarAngularModule } from 'simplebar-angular';

// Swiper Slider
import { NgxUsefulSwiperModule } from 'ngx-useful-swiper';
import { LightboxModule } from 'ngx-lightbox';

// Load Icons
import { defineElement } from 'lord-icon-element';
import lottie from 'lottie-web';
// Ng Select Module 
import { NgSelectModule } from '@ng-select/ng-select';

// Pages Routing
import { PagesRoutingModule } from "./pages-routing.module";
import { SharedModule } from "../shared/shared.module";
import { DashboardComponent } from './dashboard/dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CitiesComponent } from './cities/cities.component';
import { ProvincesComponent } from './provinces/provinces.component';
import { CountriesComponent } from './countries/countries.component';
import { DepartmentsComponent } from './departments/departments.component';
import { DesignationsComponent } from './designations/designations.component';
import { AddCityComponent } from './cities/add-city/add-city.component';
import { AddDesignationComponent } from './designations/add-designation/add-designation.component';
import { AddDepartmentComponent } from './departments/add-department/add-department.component';
import { AddCountryComponent } from './countries/add-country/add-country.component';
import { AddProvinceComponent } from './provinces/add-province/add-province.component';
import { CompaniesComponent } from './companies/companies.component';
import { AddCompanyComponent } from './companies/add-company/add-company.component';
import { UserGroupsComponent } from './user-groups/user-groups.component';
import { AddGroupComponent } from './user-groups/add-group/add-group.component';
import { RightComponent } from './right/right.component';
import { AddRightComponent } from './right/add-right/add-right.component';
import { CategoryComponent } from './category/category.component';
import { AddCategoryComponent } from './category/add-category/add-category.component';
import { AddMaintenanceComponent } from './maintenance/add-maintenance/add-maintenance.component';
import { AssignTaskComponent } from './maintenance/model/assign-task/assign-task.component';
import { ViewChecklistComponent } from './maintenance/model/view-checklist/view-checklist.component';
import { ViewSummaryComponent } from './maintenance/model/view-summary/view-summary.component';
import { CustomerCheckListComponent } from './customer-check-list/customer-check-list.component';
import { AddCustomerCheckListComponent } from './customer-check-list/add-customer-check-list/add-customer-check-list.component';
import { ContractComponent } from './setup/contract/contract.component';
import { SalesComponent } from './business/sales/sales.component';
import { PurchaseComponent } from './business/purchase/purchase.component';
import { CustomerComponent } from './accounts/customer/customer.component';
import { VendorComponent } from './accounts/vendor/vendor.component';
import { AddVendorComponent } from './accounts/vendor/add-vendor/add-vendor.component';
import { AddCustomerComponent } from './accounts/customer/add-customer/add-customer.component';
import { AddSiteComponent } from './accounts/site/add-site/add-site.component';
import { SiteComponent } from './accounts/site/site.component';
import { AddSubCategoryComponent } from './category/add-sub-category/add-sub-category.component';
import { ChecklistTypeComponent } from './checklist-type/checklist-type.component';
import { IssueTypeComponent } from './issue-type/issue-type.component';
import { AddIssueTypeComponent } from './issue-type/add-issue-type/add-issue-type.component';
import { AddManufacturerComponent } from './manufacturer/add-manufacturer/add-manufacturer.component';
import { ManufacturerComponent } from './manufacturer/manufacturer.component';
import { FeaturesComponent } from './features/features.component';
import { AddFeaturesComponent } from './features/add-features/add-features.component';
import { UnitComponent } from './setup/unit/unit.component';
import { AddUnitComponent } from './setup/unit/add-unit/add-unit.component';
import { AddHazardComponent } from './setup/hazard/add-hazard/add-hazard.component';
import { HazardComponent } from './setup/hazard/hazard.component';
import { HazardTypeComponent } from './setup/hazard-type/hazard-type.component';
import { AddHazardTypeComponent } from './setup/hazard-type/add-hazard-type/add-hazard-type.component';
import { AddProductComponent } from './setup/product/add-product/add-product.component';
import { ProductComponent } from './setup/product/product.component';
import { DiffPipe } from '../helpers/diff.pipe';
import { AddChecklistTypeComponent } from './customer-check-list/add-checklist-type/add-checklist-type.component';
import { AddChecklistCategoryComponent } from './customer-check-list/add-checklist-category/add-checklist-category.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { UserComponent } from './user/user.component';
import { RightRoleComponent } from './right-role/right-role.component';
import { AddEmployeeComponent } from './accounts/employee/add-employee/add-employee.component';
import { EmployeeComponent } from './accounts/employee/employee.component';
import { AddSalesComponent } from './business/sales/add-sales/add-sales.component';
import { ToastsContainer } from './toasts-container.component';
import { MainComponent } from './main/main.component';
import { TaskAssignmentComponent } from './maintenance/task-assignment/task-assignment.component';
import { AddPurchaseComponent } from './business/purchase/add-purchase/add-purchase.component';
import { AddContractComponent } from './setup/contract/add-contract/add-contract.component';
import { ParseDatePipe } from '../helpers/parse-date.pipe';
import { AddProductTypeComponent } from './category/add-product-type/add-product-type.component';
import { EditProductTypeComponent } from './category/edit-product-type/edit-product-type.component';
import { UserRightsComponent } from './user-rights/user-rights.component';
import { UserTypeComponent } from './user-type/user-type.component';
import { AddUserTypeComponent } from './user-type/add-user-type/add-user-type.component';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
  declarations: [
    ToastsContainer,
    DashboardComponent,
    ProvincesComponent,
    CountriesComponent,
    DepartmentsComponent,
    DesignationsComponent,
    ContractComponent,
    CitiesComponent,
    AddCityComponent,
    AddDesignationComponent,
    AddDepartmentComponent,
    AddCountryComponent,
    AddProvinceComponent,
    AddUserComponent,
    UserComponent,
    AddCompanyComponent,
    CompaniesComponent,
    UserGroupsComponent,
    AddGroupComponent,
    RightComponent,
    AddRightComponent,
    CategoryComponent,
    AddCategoryComponent,
    AddMaintenanceComponent,
    AssignTaskComponent,
    ViewChecklistComponent,
    ViewSummaryComponent,
    AddCustomerCheckListComponent,
    CustomerCheckListComponent,
    AddVendorComponent,
    VendorComponent,
    AddCustomerComponent,
    CustomerComponent,
    AddProductComponent,
    ProductComponent,
    SalesComponent,
    PurchaseComponent,
    AddSiteComponent,
    SiteComponent,
    AddSubCategoryComponent,
    ChecklistTypeComponent,
    IssueTypeComponent,
    AddIssueTypeComponent,
    AddManufacturerComponent,
    ManufacturerComponent,
    AddFeaturesComponent,
    FeaturesComponent,
    AddUnitComponent,
    UnitComponent,
    AddHazardComponent,
    HazardComponent,
    HazardTypeComponent,
    AddHazardTypeComponent,
    DiffPipe,
    AddChecklistTypeComponent,
    AddChecklistCategoryComponent,
    RightRoleComponent,
    AddEmployeeComponent,
    EmployeeComponent,
    AddSalesComponent,
    MainComponent,
    TaskAssignmentComponent,
    AddPurchaseComponent,
    PurchaseComponent,
    ContractComponent,
    AddContractComponent,
    AddProductTypeComponent,
    EditProductTypeComponent,
    UserRightsComponent,
    UserTypeComponent,
    AddUserTypeComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    NgbToastModule,
    FlatpickrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    CountToModule,
    LeafletModule,
    NgbDropdownModule,
    NgbNavModule,
    SimplebarAngularModule,
    SharedModule,
    PagesRoutingModule,
    NgbTooltip,
    NgSelectModule,
    NgxUsefulSwiperModule,
    LightboxModule,
  ],
  providers:[
    DiffPipe,
    ParseDatePipe
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesModule {
  constructor() {
    defineElement(lottie.loadAnimation);
  }
}
