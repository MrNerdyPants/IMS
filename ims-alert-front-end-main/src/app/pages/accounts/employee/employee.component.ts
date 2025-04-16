import { Component } from '@angular/core';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent {
  allData: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Employee';

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Employee', active: true }
    ];
    this.getAllData();
  }

getAllData(): void {
  this.genericService.getData('right/get-employee', true)
    .subscribe(
      (data: any) => {
        this.allData = data;
      },
      (error: any) => {
      });
}

openEdit(id: any, editData: any): void {
  const modalRef = this.modalService.open(AddEmployeeComponent);
  modalRef.componentInstance.id = id;
  modalRef.componentInstance.editData = editData;
  modalRef.result.then((result) => {
    if (result === 'success') {
      this.getAllData();
    }
  }, (reason) => {
  });
}


openDeleteModal(id: any): void {
  const modalRef = this.modalService.open(ConfirmationModalComponent);
  modalRef.result.then((result: string) => {
    if (result.toString().trim() === 'YES') {
      this.deleteData(id);
    }
  }, (reason) => {
  });
}


deleteData(id: any): any {
  this.genericService.deleteData('right/delete-employee/' + id)
    .subscribe(
      (data: any) => {
        this.toaster.success('Record Deleted Successfully !');
        this.getAllData();
      },
      (error: any) => {
        this.toaster.error('Failed To Delete Record !');
      });
}

openAdd(): void {
  const modalRef = this.modalService.open(AddEmployeeComponent);
  modalRef.result.then((result) => {
    if (result === 'success') {
      this.getAllData();
    }
  }, (reason) => {
  });
}
}
