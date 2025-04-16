import { Component } from '@angular/core';
import { AddUserComponent } from './add-user/add-user.component';
import { FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  allData: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'User';

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'User', active: true }
    ];
    this.getAllUsers();
  }

  getAllUsers(): void {
    this.genericService.getData('auth/get-user')
      .subscribe(
        (data: any) => {
          console.log(data);
          this.allData = data;
        },
        (error: any) => {
        });
  }

  openEdit(id: any, editData: any): void {
    const modalRef = this.modalService.open(AddUserComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.editData = editData;
    modalRef.result.then((result) => {
      if (result === 'success') {
        this.getAllUsers();
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
    this.genericService.deleteData('auth/delete-user/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllUsers();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  openAdd(): void {
    const modalRef = this.modalService.open(AddUserComponent);
    modalRef.result.then((result) => {
      if (result === 'success') {
        this.getAllUsers();
      }
    }, (reason) => {
    });
  }
}
