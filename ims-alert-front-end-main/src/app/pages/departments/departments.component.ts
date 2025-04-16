import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { AddDepartmentComponent } from 'src/app/pages/departments/add-department/add-department.component';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-departments',
  templateUrl: './departments.component.html',
  styleUrls: ['./departments.component.scss']
})
export class DepartmentsComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Department';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Department', active: true }
    ];
    this.getDepartments();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddDepartmentComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getDepartments();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any): void {
    const modalRef = this.modalService.open(AddDepartmentComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getDepartments();
      }
    }, (reason: any) => {
    });
  }

  openDelete(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteStatus(id);
      }
    }, (reason: any) => {
    });
  }

  deleteStatus(id: any): any {
    this.genericService.deleteData('settings/delete-department/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getDepartments();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getDepartments(): void {
    this.genericService.getData('settings/get-department')
      .subscribe((data: any) => {
        this.getResult = data;
      }, (error: any) => {
        this.toaster.error('Error in loading Departments...')
      });
  }
}
