import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-view-summary',
  templateUrl: './view-summary.component.html',
  styleUrls: ['./view-summary.component.css']
})
export class ViewSummaryComponent {

  complaint: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    if (this.route.snapshot.params['id']) {
      this.getSalesById(this.route.snapshot.params['id']);
    }
  }

  getSalesById(id: any) {
    this.genericService.getData('maintenance/get-maintenance-by-id/' + id)
      .subscribe((data: any) => {
        this.complaint = data?.response;
        console.log(this.complaint);
      },
        (error: any) => {

        });
  }
}
