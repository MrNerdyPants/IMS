import { Component } from '@angular/core';
import { AddCustomerCheckListComponent } from './add-customer-check-list/add-customer-check-list.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';
import { AddChecklistTypeComponent } from './add-checklist-type/add-checklist-type.component';
import { AddChecklistCategoryComponent } from './add-checklist-category/add-checklist-category.component';
import { AuthenticationService } from 'src/app/services/auth.service';
import { AddIssueTypeComponent } from '../issue-type/add-issue-type/add-issue-type.component';

@Component({
  selector: 'app-customer-check-list',
  templateUrl: './customer-check-list.component.html',
  styleUrls: ['./customer-check-list.component.css']
})
export class CustomerCheckListComponent {
  checklistTypes: any;
  checkListCategory: any;
  getResult: any;
  issueTypes: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Check List';
  isAdmin:boolean = false;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,public auth:AuthenticationService,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Check List', active: true }
    ];
    this.getAllCategory();
    this.getCheckListTypes();
    this.getIssueTypes();
    this.getCheckListCategory();
    this.isAdmin = this.auth?.currentUserValue?.id === '1' ? true : false;
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddCustomerCheckListComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
      }
    }, (reason: any) => {
    });
  }

openAddIssueType(): void {
    const modalRef = this.modalService.open(AddIssueTypeComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getIssueTypes();
      }
    }, (reason: any) => {
    });
  }

  openEditIssueType(id: any, title: any): void {
    const modalRef = this.modalService.open(AddIssueTypeComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getIssueTypes();
      }
    }, (reason: any) => {
    });
  }

  openDeleteIssueType(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteIssueType(id);
      }
    }, (reason: any) => {
    });
  }

  deleteIssueType(id: any): any {
    this.genericService.deleteData('settings/delete-issue-type/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getIssueTypes();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getIssueTypes(): void {
    this.genericService.getData('settings/get-issue-type', true)
      .subscribe((data: any) => {
        this.issueTypes = data;
      }, (error: any) => {
        this.toaster.error('Error in loading IssueTypes...')
      });
  }

  openAddCheckListType(): void {
    const modalRef = this.modalService.open(AddChecklistTypeComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCheckListTypes();
      }
    }, (reason: any) => {
    });
  }


  openAddCheckListCategory(): void {
    const modalRef = this.modalService.open(AddChecklistCategoryComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCheckListCategory();
      }
    }, (reason: any) => {
    });
  }

  openEditCheckListType(id: any, title: any): void {
    const modalRef = this.modalService.open(AddChecklistTypeComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCheckListTypes();
      }
    }, (reason: any) => {
    });
  }

  openEditCheckListCategory(id: any, title: any): void {
    const modalRef = this.modalService.open(AddChecklistCategoryComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCheckListCategory();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, titles: any, activeInd: any, type: any, checkListCategoryId: any, categoryId: any, productType: any): void {
    const modalRef = this.modalService.open(AddCustomerCheckListComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.titles = titles;
    modalRef.componentInstance.activeStatus = activeInd;
    modalRef.componentInstance.typeId = type;
    modalRef.componentInstance.checkListCategoryId = checkListCategoryId;
    modalRef.componentInstance.categoryId = categoryId;
    modalRef.componentInstance.productType = productType;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
      }
    }, (reason: any) => {
    });
  }

  openDelete(id: any, type: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        if (type === 'type') {
          this.deleteCheckListType(id);
        } else if (type === 'category') {
          this.deleteCheckListCategory(id);
        } else if (type === 'list') {
          this.deleteStatus(id);
        }
      }
    }, (reason: any) => {
    });
  }

  deleteStatus(id: any): any {
    this.genericService.deleteData('settings/delete-customer-check-list/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllCategory();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  deleteCheckListType(id: any): any {
    this.genericService.deleteData('settings/delete-checklist-type/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getCheckListTypes();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  deleteCheckListCategory(id: any): any {
    this.genericService.deleteData('settings/delete-checklist-category/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getCheckListCategory();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-customer-check-list', true)
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      },
        (error: any) => {

        });
  }



  getCheckListTypes(): void {
    this.genericService.getData('settings/get-checklist-type')
      .subscribe((data: any) => {
        this.checklistTypes = data;
      },
        (error: any) => {

        });
  }


  getCheckListCategory(): void {
    this.genericService.getData('settings/get-checklist-category')
      .subscribe((data: any) => {
        this.checkListCategory = data;
        console.log(data);
      },
        (error: any) => {

        });
  }

}
