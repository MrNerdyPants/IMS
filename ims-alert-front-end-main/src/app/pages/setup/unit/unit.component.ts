import { Component } from '@angular/core';
import { AddUnitComponent } from './add-unit/add-unit.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-unit',
  templateUrl: './unit.component.html',
  styleUrls: ['./unit.component.css']
})
export class UnitComponent {
  getResult:any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Unit';

  constructor(private toaster: ToastService, private modalService: NgbModal,
              public genericService: GenericService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Unit', active: true }
    ];
    this.getAllCategory();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddUnitComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any, activeInd: any): void {
    console.log(title);
    const modalRef = this.modalService.open(AddUnitComponent);
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
    this.genericService.deleteData('settings/delete-unit/' + id)
      .subscribe(
        (        data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllCategory();
        },
        (        error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-unit')
      .subscribe((data: any) => {
          this.getResult = data;
        },
        (        error: any) => {

        });
  }

}
