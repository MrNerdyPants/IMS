import { Component } from '@angular/core';
import { AddVendorComponent } from './add-vendor/add-vendor.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-vendor',
  templateUrl: './vendor.component.html',
  styleUrls: ['./vendor.component.css']
})
export class VendorComponent {
  getResult:any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Vendor';
  
  constructor(private toaster: ToastService, private modalService: NgbModal,
              public genericService: GenericService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Vendor', active: true }
    ];
    this.getAllData();
  }

  openAdd(): void {
    const modalRef = this.modalService.open(AddVendorComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllData();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, data: any): void {
    const modalRef = this.modalService.open(AddVendorComponent);
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
    this.genericService.deleteData('settings/delete-vendor/' + id)
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
    this.genericService.getData('settings/get-vendor',true)
      .subscribe((data: any) => {
          this.getResult = data;
        },
        (        error: any) => {

        });
  }
}
