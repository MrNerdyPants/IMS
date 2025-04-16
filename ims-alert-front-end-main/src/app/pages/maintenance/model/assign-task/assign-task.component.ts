import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { ParseDatePipe } from 'src/app/helpers/parse-date.pipe';

@Component({
  selector: 'app-assign-task',
  templateUrl: './assign-task.component.html',
  styleUrls: ['./assign-task.component.css']
})
export class AssignTaskComponent {
  addForm: FormGroup = new FormGroup(
    {
      employee: new FormControl('', Validators.required),
      from: new FormControl('', Validators.required),
      to: new FormControl('', Validators.required),
    }
  );
  employees: any;
  loading = true;
  @Input() data: any;
  minDate = new Date();

  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService, private parseDate: ParseDatePipe) {
  }

  ngOnInit(): void {
    console.log(this.data);
    if (this.data?.visitFrom) {
      // console.log(this.data?.visitFrom);
      // console.log(this.parseDate.transform(this.data?.visitFrom));
      this.addForm.patchValue({
        from: this.parseDate.transform(this.data?.visitFrom),
        to: this.parseDate.transform(this.data?.visitTo)
      });
    }
    this.getAllEmployee();
  }

  getAllEmployee(): void {
    this.genericService.getData('right/get-employee', true)
      .subscribe((data: any) => {
        this.employees = data;
        if (this.data?.employee) {
          this.addForm.patchValue({
            employee: this.data?.employee?.id
          });
        }
        this.loading = false;
      },
        (error: any) => {

        });
  }

  validateAllFormFields(formGroup: FormGroup | FormArray): void {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      } else if (control instanceof FormArray) {
        this.validateAllFormFields(control);
      }
    });
  }

  onSubmit(): void {
    if (this.addForm.valid) {
      const obj = {
        id: this.data.id,
        employee: undefined,
        visitFrom: undefined,
        visitTo: undefined,
        status: "assigned",
        detail: this.data.detail
      };

      obj.employee = this.addForm.value.employee;
      obj.visitFrom = this.addForm.value.from;
      obj.visitTo = this.addForm.value.to;
      this.genericService.saveData('maintenance/save-maintenance', obj)
        .subscribe(
          (data: any) => {
            console.log(data);
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.activeModal.close('success');
            } else {
              this.toaster.error(data.message);
            }

          },
          (error: any) => {
            this.toaster.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addForm);
    }
  }
}
