import { Component } from '@angular/core';
import { AddSiteComponent } from './add-site/add-site.component';
import { FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.css']
})
export class SiteComponent {
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Branch';
  allData: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    // private rightService: RightsService,
    public genericService: GenericService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.getAllSitesList();    
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Branch', active: true }
    ];
  }

  getAllSitesList(): void {
    this.genericService.getData('right/get-site', true)
      .subscribe(
        (data: any) => {
          this.allData = data;
        },
        (error: any) => {
        });
  }

  openEdit(id: any, editData: any): void {
    const modalRef = this.modalService.open(AddSiteComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.editData = editData;
    modalRef.result.then((result) => {
      if (result === 'success') {
        this.getAllSitesList();
      }
    }, (reason) => {
    });
  }


  openDeleteModal(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteData(id);
      }
    }, (reason) => {
    });
  }


  deleteData(id: any): any {
    this.genericService.deleteData('right/delete-site/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllSitesList();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  openAdd(): void {
    const modalRef = this.modalService.open(AddSiteComponent);
    modalRef.result.then((result) => {
      if (result === 'success') {
        this.getAllSitesList();
      }
    }, (reason) => {
    });
  }
}
