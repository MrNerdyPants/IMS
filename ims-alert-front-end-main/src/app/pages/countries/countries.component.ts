import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddCountryComponent } from './add-country/add-country.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-countries',
  templateUrl: './countries.component.html',
  styleUrls: ['./countries.component.scss']
})
export class CountriesComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Country';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Country', active: true }
    ];
    this.getCountrys();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddCountryComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCountrys();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any): void {
    const modalRef = this.modalService.open(AddCountryComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCountrys();
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
    this.genericService.deleteData('settings/delete-country/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getCountrys();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getCountrys(): void {
    this.genericService.getData('settings/get-country')
      .subscribe((data: any) => {
        this.getResult = data;
      }, (error: any) => {
        this.toaster.error('Error in loading Countrys...')
      });
  }
}
