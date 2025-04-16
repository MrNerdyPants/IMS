import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddCompanyComponent } from './add-company/add-company.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.scss']
})
export class CompaniesComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Company';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Company', active: true }
    ];
    this.getCompany();
  }

  openAddModal(): void {
    const modalRef = this.modalService.open(AddCompanyComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCompany();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, editData: any): void {
    const modalRef = this.modalService.open(AddCompanyComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.editData = editData;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCompany();
      }
    }, (reason: any) => {
    });
  }

  openDelete(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteCompany(id);
      }
    }, (reason: any) => {
    });
  }

  deleteCompany(id: any): any {
    this.genericService.deleteData('right/delete-company/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getCompany();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getCompany(): void {
    this.genericService.getData('right/get-company')
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      }, (error: any) => {
        this.toaster.error('Error in loading Cities...')
      });
  }
}
