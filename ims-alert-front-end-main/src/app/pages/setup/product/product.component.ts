import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { AuthenticationService } from 'src/app/services/auth.service';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/pages/toast.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  getResult: any;
  currentUser: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Product';
  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private router: Router,
    public authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Product', active: true }
    ];
    this.currentUser = this.authService.currentUserValue;
    console.log(this.currentUser);
    this.getAllProduct();
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
    this.genericService.deleteData('product/' + id)
      .subscribe(
        (data: any) => {
          this.toaster.success('Record Deleted Successfully !');
          this.getAllProduct();
        },
        (error: any) => {
          this.toaster.error('Failed To Delete Record !');
        });
  }

  getAllProduct(): void {
    this.genericService.getData('product/get-product', true)
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      },
        (error: any) => {

        });
  }

  openEdit(id: any): any {
    this.router.navigate(["/edit-product/" + id]);
  }
}
