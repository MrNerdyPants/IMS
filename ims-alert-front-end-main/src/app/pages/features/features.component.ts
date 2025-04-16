import { Component } from '@angular/core';
import { AddFeaturesComponent } from './add-features/add-features.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent {
  getResult:any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Feature';
  constructor(private toaster: ToastService, private modalService: NgbModal,
              public genericService: GenericService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Feature', active: true }
    ];
    this.getAllData();
  }

  openAdd(): void {
    const modalRef = this.modalService.open(AddFeaturesComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllData();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any, subCategory: any,icon:any): void {
    const modalRef = this.modalService.open(AddFeaturesComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.subCategory = subCategory;
    modalRef.componentInstance.icon = icon;
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
    this.genericService.deleteData('settings/delete-feature/' + id)
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
    this.genericService.getData('settings/get-feature')
      .subscribe((data: any) => {
          this.getResult = data;
        },
        (        error: any) => {

        });
  }

}
