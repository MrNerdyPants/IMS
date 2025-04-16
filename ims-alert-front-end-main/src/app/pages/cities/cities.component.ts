import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddCityComponent } from './add-city/add-city.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';


@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.scss']
})
export class CitiesComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'City';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'City', active: true }
    ];
    this.getCities();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddCityComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCities();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, stateId: any, countryId: any, title: any): void {
    const modalRef = this.modalService.open(AddCityComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.stateId = stateId;
    modalRef.componentInstance.countryId = countryId;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getCities();
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
    this.genericService.deleteData('settings/delete-city/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getCities();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getCities(): void {
    this.genericService.getData('settings/get-city')
      .subscribe((data: any) => {
        this.getResult = data;
      }, (error: any) => {
        this.toaster.error('Error in loading Cities...')
      });
  }
}
