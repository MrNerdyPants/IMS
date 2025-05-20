import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Component pages
import { DashboardComponent } from "./dashboard/dashboard.component";
import { CitiesComponent } from './cities/cities.component';
import { ProvincesComponent } from './provinces/provinces.component';
import { CountriesComponent } from './countries/countries.component';
import { DepartmentsComponent } from './departments/departments.component';
import { DesignationsComponent } from './designations/designations.component';
import { UserGroupsComponent } from './user-groups/user-groups.component';
import { CompaniesComponent } from './companies/companies.component';
import { RightComponent } from './right/right.component';
import { AccessDeniedComponent } from '../access-denied/access-denied.component';
import { CategoryComponent } from './category/category.component';
import { AssignTaskComponent } from './maintenance/model/assign-task/assign-task.component';
import { TaskAssignmentComponent } from './maintenance/task-assignment/task-assignment.component';
import { CustomerCheckListComponent } from './customer-check-list/customer-check-list.component';
import { ContractComponent } from './setup/contract/contract.component';
import { SalesComponent } from './business/sales/sales.component';
import { PurchaseComponent } from './business/purchase/purchase.component';
import { CustomerComponent } from './accounts/customer/customer.component';
import { VendorComponent } from './accounts/vendor/vendor.component';
import { SiteComponent } from './accounts/site/site.component';
import { IssueTypeComponent } from './issue-type/issue-type.component';
import { ManufacturerComponent } from './manufacturer/manufacturer.component';
import { FeaturesComponent } from './features/features.component';
import { UnitComponent } from './setup/unit/unit.component';
import { HazardComponent } from './setup/hazard/hazard.component';
import { HazardTypeComponent } from './setup/hazard-type/hazard-type.component';
import { ProductComponent } from './setup/product/product.component';
import { AddProductComponent } from './setup/product/add-product/add-product.component';
import { UserComponent } from './user/user.component';
import { RightRoleComponent } from './right-role/right-role.component';
import { EmployeeComponent } from './accounts/employee/employee.component';
import { AddMaintenanceComponent } from './maintenance/add-maintenance/add-maintenance.component';
import { AddSalesComponent } from './business/sales/add-sales/add-sales.component';
import { MainComponent } from './main/main.component';
import { AddPurchaseComponent } from './business/purchase/add-purchase/add-purchase.component';
import { AddContractComponent } from './setup/contract/add-contract/add-contract.component';
import { ViewSummaryComponent } from './maintenance/model/view-summary/view-summary.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  {
    path: "", component: MainComponent, children: [
      { path: "", component: DashboardComponent },
      { path: "category", component: CategoryComponent },
      { path: "issue-type", component: IssueTypeComponent },
      { path: "features", component: FeaturesComponent },
      { path: "unit", component: UnitComponent },
      { path: "hazard", component: HazardComponent },
      { path: "products", component: ProductComponent },
      { path: "edit-product/:id", component: AddProductComponent },
      { path: "customer-check-list", component: CustomerCheckListComponent },
      { path: "users", component: UserComponent },
      { path: "companies", component: CompaniesComponent },
      { path: "sale", component: SalesComponent },
      { path: "edit-sales/:id", component: AddSalesComponent },
      { path: "quotation", component: AccessDeniedComponent },
      { path: "purchase", component: PurchaseComponent },
      { path: "edit-purchase/:id", component: AddPurchaseComponent },
      { path: "customer", component: CustomerComponent },
      { path: "vendor", component: VendorComponent },
      { path: "dashboard", component: DashboardComponent },
      { path: "city", component: CitiesComponent },
      { path: "province", component: ProvincesComponent },
      { path: "country", component: CountriesComponent },
      { path: "branch", component: SiteComponent },
      { path: "department", component: DepartmentsComponent },
      { path: "designation", component: DesignationsComponent },
      { path: "contract", component: ContractComponent },
      { path: "right", component: RightComponent },
      { path: "user-groups", component: UserGroupsComponent },
      { path: "role-right", component: RightRoleComponent },
      { path: "user-right", component: RightComponent },
      { path: "user-types", component: RightComponent },
      { path: "area", component: AccessDeniedComponent },
      { path: "language", component: AccessDeniedComponent },
      { path: "product-category", component: CategoryComponent },
      { path: "priority-type", component: AccessDeniedComponent },
      { path: "work-indicator", component: AccessDeniedComponent },
      { path: "hazard-type", component: HazardTypeComponent },
      { path: "task-type", component: AccessDeniedComponent },
      { path: "ticket-type", component: AccessDeniedComponent },
      { path: "leave-type", component: AccessDeniedComponent },
      { path: "alert-type", component: AccessDeniedComponent },
      { path: "user-type", component: AccessDeniedComponent },
      { path: "insight", component: AccessDeniedComponent },
      { path: "alerts", component: AccessDeniedComponent },
      { path: "ticket-assignment", component: TaskAssignmentComponent },
      { path: "ticket-issue", component: AddMaintenanceComponent },
      { path: "view-summary/:id", component: ViewSummaryComponent },
      { path: "employees", component: EmployeeComponent },
      { path: "expense-claim", component: AccessDeniedComponent },
      { path: "leave-application", component: AccessDeniedComponent },
      { path: "check-maintenence", component: AccessDeniedComponent },
      { path: "check-training", component: AccessDeniedComponent },
      { path: "check-advisor", component: AccessDeniedComponent },
      { path: "message-alert", component: AccessDeniedComponent },
      { path: "manufacturer", component: ManufacturerComponent },
      { path: "balance", component: AccessDeniedComponent },
      { path: "leave-balance", component: AccessDeniedComponent },
      { path: 'add-contract', component: AddContractComponent },
      { path: 'edit-contract/:id', component: AddContractComponent },
      { path: 'contracts', component: ContractComponent },
      { path: "access-denied", component: AccessDeniedComponent },
      { path: "profile", component: ProfileComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
