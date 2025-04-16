import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { AddCategoryComponent } from './add-category/add-category.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { AddSubCategoryComponent } from './add-sub-category/add-sub-category.component';
import { AuthenticationService } from 'src/app/services/auth.service';
import { AddProductTypeComponent } from './add-product-type/add-product-type.component';
import { ToastService } from 'src/app/pages/toast.service';
import { EditProductTypeComponent } from './edit-product-type/edit-product-type.component';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent {
  getResult: any;
  getSubResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Category';
  adminUser: string = '';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService, private auth: AuthenticationService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Category', active: true }
    ];
    this.getMainCategorys();
    this.getSubCategory();
    // this.getProductTypes();
    this.adminUser = this.auth.currentUserValue.id;
    console.log(this.adminUser);
  }

  openAddModal(): void {
    const modalRef = this.modalService.open(AddCategoryComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getMainCategorys();
      }
    }, (reason: any) => {
    });
  }

  openAddSubModal(): void {
    const modalRef = this.modalService.open(AddSubCategoryComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getSubCategory();
      }
    }, (reason: any) => {
    });
  }

  openAddProductType(): void {
    const modalRef = this.modalService.open(AddProductTypeComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getSubCategory();
      }
    }, (reason: any) => {
    });
  }

  editProductType(productType: any, subCategory: any) {
    const modalRef = this.modalService.open(EditProductTypeComponent);
    modalRef.componentInstance.productType = productType;
    modalRef.componentInstance.subCategory = subCategory;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getSubCategory();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, activeInd: any, title: any): void {
    const modalRef = this.modalService.open(AddCategoryComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.activeStatus = activeInd;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getMainCategorys();
      }
    }, (reason: any) => {
    });
  }

  openSubEdit(id: any, categoryId: any, activeInd: any, types: any, title: any): void {
    const modalRef = this.modalService.open(AddSubCategoryComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.parentId = categoryId;
    modalRef.componentInstance.activeStatus = activeInd;
    modalRef.componentInstance.types = types;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getSubCategory();
      }
    }, (reason: any) => {
    });
  }

  openSubTypeEdit(id: any, activeInd: any, title: any): void {
    const modalRef = this.modalService.open(AddCategoryComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.activeStatus = activeInd;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getSubCategory();
      }
    }, (reason: any) => {
    });
  }

  openDelete(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteCategory(id);
      }
    }, (reason: any) => {
    });
  }

  openDeleteProductType(id: any, categoryId: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.removeProductType(id);
      }
    }, (reason: any) => {
    });
  }

  removeProductType(productTypeId: any): void {
    this.genericService.deleteData('settings/delete-product-type/' + productTypeId)
      .subscribe((data) => {
        this.getSubCategory();
        this.toaster.success('Record Deleted Successfully !');
      }, (error: any) => {
        this.toaster.error('Failed To Delete Record !');
      });
  }

  deleteCategory(id: any): any {
    this.genericService.deleteData('settings/delete-category/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getMainCategorys();
          this.getSubCategory();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getMainCategorys(): void {
    this.genericService.getData('settings/get-category')
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      }, (error: any) => {
        this.toaster.error('Error in loading Cities...')
      });
  }


  getSubCategory(): void {
    this.genericService.getData('settings/get-sub-category')
      .subscribe((data: any) => {
        this.getSubResult = data;
        console.log(data);
      },
        (error: any) => {

        });
  }
}
