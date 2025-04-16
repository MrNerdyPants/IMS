import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-view-checklist',
  templateUrl: './view-checklist.component.html',
  styleUrls: ['./view-checklist.component.css']
})
export class ViewChecklistComponent implements OnInit {
  @Input() data: any;

  constructor(
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }
  ngOnInit(): void {
    // console.log(this.data);
  }

}

