import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { AddCustomerComponent } from './add-customer/add-customer.component';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from '../../toast.service';


@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent {
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Customer';
  getResult:any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
              public genericService: GenericService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllData();
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Customer', active: true }
    ];
  }

  openAdd(): void {
    const modalRef = this.modalService.open(AddCustomerComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllData();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, data: any): void {
    const modalRef = this.modalService.open(AddCustomerComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.editData = data;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllData();
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
    this.genericService.deleteData('settings/delete-customer/' + id)
      .subscribe(
        (        data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllData();
        },
        (        error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getAllData(): void {
    this.genericService.getData('settings/get-customer',true)
      .subscribe((data: any) => {
          this.getResult = data;          
          console.log(data);
        },
        (        error: any) => {

        });
  }
}
