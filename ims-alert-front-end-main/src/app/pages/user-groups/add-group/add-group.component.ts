import { Component, Input } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.component.html',
  styleUrls: ['./add-group.component.scss']
})
export class AddGroupComponent {
  addForm: any;
  categories: any;
  loading = false;
  states: any;

  @Input() id: any;
  @Input() title: any;
  @Input() activeStatus: any;

  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    this.addForm = new FormGroup({
      title: new FormControl('', Validators.required),
      activeStatus: new FormControl(true)
    });
    if (this.title) {
      this.addForm.controls.title.value = this.title;
    }
    this.addForm.controls.activeStatus.value = this.activeStatus === 'Y';
    console.log(this.addForm.controls);
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
    console.log(this.addForm.valid)
    if (this.addForm.valid) {
      const obj = {
        id: undefined,
        name: undefined,
        activeInd: ''
      };
      console.log(this.addForm.value.activeStatus);
      obj.name = this.addForm.value.title;
      obj.activeInd = this.addForm.value.activeStatus ? 'Y' : 'N';
      obj.id = this.id !== 'undefined' ? this.id : undefined;
      console.log(obj)
      this.genericService.saveData('right/save-group', obj)
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
