import { Component } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationModalComponent } from 'src/app/common/confirmation-modal/confirmation-modal.component';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.css']
})
export class PurchaseComponent {
  getResult: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private router: Router) {
  }

  ngOnInit(): void {
    this.getAllData();
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
    this.genericService.deleteData('business/delete-purchase/' + id)
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
    this.genericService.getData('business/get-purchase', true)
      .subscribe((data: any) => {
        this.getResult = data;
        console.log(data);
      },
        (error: any) => {

        });
  }

  openEdit(id: any): any {
    const navigationExtras: NavigationExtras = {
      state: {
        id: id,
      }
    };
    this.router.navigate([`edit-purchase/${id}`], navigationExtras);
  }
}
