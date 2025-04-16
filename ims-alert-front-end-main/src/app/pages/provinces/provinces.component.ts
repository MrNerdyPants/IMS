import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddProvinceComponent } from './add-province/add-province.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-provinces',
  templateUrl: './provinces.component.html',
  styleUrls: ['./provinces.component.scss']
})
export class ProvincesComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Province';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Province', active: true }
    ];
    this.getProvinces();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddProvinceComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getProvinces();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, countryId: any, title: any): void {
    const modalRef = this.modalService.open(AddProvinceComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.countryId = countryId;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getProvinces();
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
    this.genericService.deleteData('settings/delete-state/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getProvinces();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getProvinces(): void {
    this.genericService.getData('settings/get-state')
      .subscribe((data: any) => {
        this.getResult = data;
      }, (error: any) => {
        this.toaster.error('Error in loading Provinces...')
      });
  }
}
