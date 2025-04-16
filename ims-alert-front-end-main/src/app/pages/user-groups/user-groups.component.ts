import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddGroupComponent } from './add-group/add-group.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-user-groups',
  templateUrl: './user-groups.component.html',
  styleUrls: ['./user-groups.component.scss']
})
export class UserGroupsComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Role';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Role', active: true }
    ];
    this.getUserGroups();
  }

  openAddGroup(): void {
    const modalRef = this.modalService.open(AddGroupComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getUserGroups();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, activeInd: any, title: any): void {
    const modalRef = this.modalService.open(AddGroupComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.activeStatus = activeInd;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getUserGroups();
      }
    }, (reason: any) => {
    });
  }

  openDelete(id: any): void {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result: string) => {
      if (result.toString().trim() === 'YES') {
        this.deleteGroup(id);
      }
    }, (reason: any) => {
    });
  }

  deleteGroup(id: any): any {
    this.genericService.deleteData('right/delete-group/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getUserGroups();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getUserGroups(): void {
    this.genericService.getData('right/get-group')
      .subscribe((data: any) => {
        this.getResult = data;
      }, (error: any) => {
        this.toaster.error('Error in loading Cities...')
      });
  }
}
