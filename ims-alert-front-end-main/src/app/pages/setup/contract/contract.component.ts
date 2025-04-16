import { Component } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { AuthenticationService } from 'src/app/services/auth.service';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css']
})
export class ContractComponent {
  getResult: any;
  currentUser: any;
  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Contract';
  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private router: Router,
    public authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Contract', active: true }
    ];
    this.currentUser = this.authService.currentUserValue;
    console.log(this.currentUser.companyId);
    this.getAllProduct();
  }

  open() {
    this.router.navigate(['/add-contract']);
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
    this.genericService.deleteData('business/delete-contract/' + id)
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
    this.genericService.getData('business/get-contract-by-company', true)
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(this.getResult);
      },
        (error: any) => {

        });
  }

  openEdit(id: any): any {
    // const navigationExtras: NavigationExtras = {
    //   state: {
    //     id: id,
    //   }
    // };
    this.router.navigate([`edit-contract/${id}`]
      // , navigationExtras
    );
  }
}
