import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddRightComponent } from './add-right/add-right.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-right',
  templateUrl: './right.component.html',
  styleUrls: ['./right.component.scss']
})
export class RightComponent {
  allRights: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Right';

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.getAllRightsList();
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Right', active: true }
    ];
  }

  getAllRightsList(): void {
    this.genericService.getData('right/get-rights')
      .subscribe(
        (data: any) => {
          this.allRights = data;
          console.log(data);
        },
        (error: any) => {
        });
  }

  openEditRoles(id: any, title: any, url: any, rightSort: any, parentId: any, activeInd: any, displayInd: any): void {
    const modalRef = this.modalService.open(AddRightComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.url = url;
    modalRef.componentInstance.sort = rightSort;
    modalRef.componentInstance.parent = parentId;
    modalRef.componentInstance.activeInd = activeInd;
    modalRef.componentInstance.displayInd = displayInd;
    modalRef.result.then((result) => {
      if (result === 'success') {
        this.getAllRightsList();
      }
    }, (reason) => {
    });
  }


  openDeleteModal(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteRoles(id);
      }
    }, (reason) => {
    });
  }


  deleteRoles(id: any): any {
    this.genericService.deleteData('right/delete-right/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllRightsList();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  openAddRights(): void {
    const modalRef = this.modalService.open(AddRightComponent);
    modalRef.result.then((result) => {
      if (result === 'success') {
        this.getAllRightsList();
      }
    }, (reason) => {
    });
  }
}
