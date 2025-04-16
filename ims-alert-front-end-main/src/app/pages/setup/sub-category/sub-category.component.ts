import { Component } from '@angular/core';
import { AddSubCategoryComponent } from './add-sub-category/add-sub-category.component';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { GenericService } from 'src/app/services/generic/generic.service';

@Component({
  selector: 'app-sub-category',
  templateUrl: './sub-category.component.html',
  styleUrls: ['./sub-category.component.css']
})
export class SubCategoryComponent {
  getResult:any;

  constructor(private toaster: ToastrService, private modalService: NgbModal,
              public genericService: GenericService, private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllCategory();
  }

  openAddStatus(): void {
    const modalRef = this.modalService.open(AddSubCategoryComponent);
    modalRef.result.then((result: string) => {
      if (result === 'success') {
        this.getAllCategory();
      }
    }, (reason: any) => {
    });
  }

  openEdit(id: any,parentId:any, title: any,types:any, activeInd: any): void {
    const modalRef = this.modalService.open(AddSubCategoryComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.parentId = parentId;
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.types = types;
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
    this.genericService.deleteData('settings/delete-category/' + id)
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
    this.genericService.getData('settings/get-sub-category')
      .subscribe((data: any) => {
          this.getResult = data;
        },
        (        error: any) => {

        });
  }

}
