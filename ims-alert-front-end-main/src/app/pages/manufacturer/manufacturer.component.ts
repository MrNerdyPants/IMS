import { Component } from '@angular/core';
import { AddManufacturerComponent } from './add-manufacturer/add-manufacturer.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';


@Component({
  selector: 'app-manufacturer',
  templateUrl: './manufacturer.component.html',
  styleUrls: ['./manufacturer.component.css']
})
export class ManufacturerComponent {
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Manufacturer';
  getResult: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Manufacturer', active: true }
    ];

    this.getAllData();
  }

  openAdd(): void {
    const modalRef = this.modalService.open(AddManufacturerComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllData();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, data: any): void {
    const modalRef = this.modalService.open(AddManufacturerComponent);
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
    this.genericService.deleteData('settings/delete-manufacturer/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllData();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getAllData(): void {
    this.genericService.getData('settings/get-manufacturer', true)
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      },
        (error: any) => {

        });
  }
}
