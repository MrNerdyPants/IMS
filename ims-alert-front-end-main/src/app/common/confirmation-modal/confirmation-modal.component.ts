import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})
export class ConfirmationModalComponent {

  constructor(public activeModal: NgbActiveModal) { }
  ngOnInit(): void { }

  confirmAction(action: string): void {
    this.activeModal.close(action);
  }
}
