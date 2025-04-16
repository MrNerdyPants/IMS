import { Component } from '@angular/core';
import { ViewChecklistComponent } from '../model/view-checklist/view-checklist.component';
import { AssignTaskComponent } from '../model/assign-task/assign-task.component';
import { GenericService } from 'src/app/services/generic.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-task-assignment',
  templateUrl: './task-assignment.component.html',
  styleUrls: ['./task-assignment.component.css']
})
export class TaskAssignmentComponent {
  getResults: any;

  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Task Assignment';
  constructor(public genericService: GenericService, private modalService: NgbModal, private router:Router) {
  }

  ngOnInit(): void {

    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Task Assignment', active: true }
    ];
    this.getAllComplaints();
  }

  getAllComplaints(): void {
    this.genericService.getData('maintenance/get-maintenance', true)
      .subscribe(
        data => {
          console.log(this.getResults);
          this.getResults = data;
        },
        error => {
        });
  }

  viewCheckList(detail: any) {
    const modalRef = this.modalService.open(ViewChecklistComponent);
    modalRef.componentInstance.data = detail;
    modalRef.result.then((result: string) => {
    }, (reason: any) => {
    });

  }

  assign(detail: any) {
    const modalRef = this.modalService.open(AssignTaskComponent);
    modalRef.componentInstance.data = detail;
    modalRef.result.then((result: string) => {
      if (result === "success") {
        this.getAllComplaints();
      }
    }, (reason: any) => {

    });

  }

  viewSummary(summaryId: any) {
    this.router.navigate([`view-summary/${summaryId}`]);
  }


}
