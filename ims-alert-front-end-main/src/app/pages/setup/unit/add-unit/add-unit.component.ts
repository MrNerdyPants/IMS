import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-unit',
  templateUrl: './add-unit.component.html',
  styleUrls: ['./add-unit.component.css']
})
export class AddUnitComponent {
  addStatus: FormGroup = new FormGroup(
    {
      title: new FormControl('', Validators.required)
    }
  );

  @Input() id: any;
  @Input() title: any;

  constructor(private toastr: ToastService,
              public activeModal: NgbActiveModal, public genericService: GenericService,
              private toaster: ToastService) {
  }

  ngOnInit(): void {
    if (this.id !== '') {
      this.addStatus.patchValue({
        title: this.title
      });
    }
  }

  validateAllFormFields(formGroup: FormGroup | FormArray): void {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      } else if (control instanceof FormArray) {
        this.validateAllFormFields(control);
      }
    });
  }

  onSubmit(): void {
    if (this.addStatus.valid) {
      const obj = {
        id: undefined,
        name: undefined
      };

      if(this.id){
        obj.id = this.id;
      }else {
        delete obj.id;
      }
      obj.name = this.addStatus.value.title;
      this.genericService.saveData('settings/save-unit',obj)
        .subscribe(
          (data: any) => {
            if(data.code === 200){
              this.toaster.success(data.message);
              this.activeModal.close('success');
            }else {
              this.toaster.error(data.message);
              this.activeModal.close('success');
            }          
            
          },
          (error: any) => {
            this.toastr.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addStatus);
    }
  }
}
