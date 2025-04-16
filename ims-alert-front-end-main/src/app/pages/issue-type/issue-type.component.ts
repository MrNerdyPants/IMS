import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { AddIssueTypeComponent } from './add-issue-type/add-issue-type.component';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-issue-type',
  templateUrl: './issue-type.component.html',
  styleUrls: ['./issue-type.component.scss']
})
export class IssueTypeComponent {
  getResult: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Issue Type';
  constructor(private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private toaster: ToastService) {

  }
  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Issue Type', active: true }
    ];
    this.getIssueTypes();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddIssueTypeComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getIssueTypes();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any): void {
    const modalRef = this.modalService.open(AddIssueTypeComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.title = title;
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getIssueTypes();
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
    this.genericService.deleteData('settings/delete-issue-type/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getIssueTypes();
        }, (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getIssueTypes(): void {
    this.genericService.getData('settings/get-issue-type', true)
      .subscribe((data: any) => {
        this.getResult = data;
      }, (error: any) => {
        this.toaster.error('Error in loading IssueTypes...')
      });
  }
}
