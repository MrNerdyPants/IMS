import { Component, Input } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-checklist-type',
  templateUrl: './add-checklist-type.component.html',
  styleUrls: ['./add-checklist-type.component.scss']
})
export class AddChecklistTypeComponent {
  addForm: any;
  loading = false;
  states: any;

  @Input() id: any;
  @Input() title: any;

  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    this.addForm = new FormGroup({
      title: new FormControl('', Validators.required)
    });
    if (this.title) {
      this.addForm.controls.title.value = this.title;
    }
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
        id: undefined,
        title: undefined
      };
      obj.title = this.addForm.value.title;
      obj.id = this.id !== 'undefined' ? this.id : undefined;
      this.genericService.saveData('settings/save-checklist-type', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.activeModal.close('success');
            } else {
              this.toaster.error(data.message);
            }
          }, (error: any) => {
            this.toaster.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addForm);
    }
  }

}
