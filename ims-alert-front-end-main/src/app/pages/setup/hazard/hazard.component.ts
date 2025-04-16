import { Component } from '@angular/core';
import { AddHazardComponent } from './add-hazard/add-hazard.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-hazard',
  templateUrl: './hazard.component.html',
  styleUrls: ['./hazard.component.css']
})
export class HazardComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Hazard';

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Hazard', active: true }
    ];
    this.getAllData();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddHazardComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllData();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, categoryId: any, code: any, type: any, description: any, symtoms: any, correctives: any): void {
    console.log(correctives, symtoms);
    const modalRef = this.modalService.open(AddHazardComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.categoryId = categoryId;
    modalRef.componentInstance.type = type;
    modalRef.componentInstance.code = code;
    modalRef.componentInstance.description = description;
    modalRef.componentInstance.symtoms = symtoms;
    modalRef.componentInstance.correctives = correctives;
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
        this.deleteAction(id);
      }
    }, (reason: any) => {
    });
  }

  deleteAction(id: any): any {
    this.genericService.deleteData('settings/delete-hazard/' + id)
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
    this.genericService.getData('settings/get-hazard')
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      },
        (error: any) => {

        });
  }
}
