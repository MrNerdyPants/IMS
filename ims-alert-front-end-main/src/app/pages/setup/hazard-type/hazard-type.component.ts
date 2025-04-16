import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddHazardTypeComponent } from './add-hazard-type/add-hazard-type.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-hazard-type',
  templateUrl: './hazard-type.component.html',
  styleUrls: ['./hazard-type.component.scss']
})
export class HazardTypeComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Hazard Type';

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Hazard Type', active: true }
    ];
    this.getAllCategory();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddHazardTypeComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any, activeInd: any): void {
    console.log(title);
    const modalRef = this.modalService.open(AddHazardTypeComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.activeStatus = activeInd;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
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
    this.genericService.deleteData('settings/delete-hazard-type/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllCategory();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-hazard-type')
      .subscribe((data: any) => {
        this.getResult = data;
      },
        (error: any) => {

        });
  }
}
