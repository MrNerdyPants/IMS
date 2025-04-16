import { Component } from '@angular/core';
import { AddIssueTypeComponent } from './add-issue-type/add-issue-type.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { GenericService } from 'src/app/services/generic/generic.service';

@Component({
  selector: 'app-issue-type',
  templateUrl: './issue-type.component.html',
  styleUrls: ['./issue-type.component.css']
})
export class IssueTypeComponent {
  getResult:any;

  constructor(private toaster: ToastrService, private modalService: NgbModal,
              public genericService: GenericService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllCategory();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddIssueTypeComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any, title: any, activeInd: any): void {
    const modalRef = this.modalService.open(AddIssueTypeComponent);
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
    this.genericService.deleteData('settings/delete-issue-type/' + id)
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
    this.genericService.getData('settings/get-issue-type',true)
      .subscribe((data: any) => {
          this.getResult = data;
        },
        (        error: any) => {

        });
  }
}
