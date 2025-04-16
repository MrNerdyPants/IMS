import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { GenericService } from 'src/app/services/generic/generic.service';

@Component({
  selector: 'app-add-issue-type',
  templateUrl: './add-issue-type.component.html',
  styleUrls: ['./add-issue-type.component.css']
})
export class AddIssueTypeComponent {
  addStatus: FormGroup = new FormGroup(
    {
      title: new FormControl('', Validators.required)
    }
  );

  @Input() id: any;
  @Input() title: any;

  constructor(private toastr: ToastrService,
              public activeModal: NgbActiveModal, public genericService: GenericService,
              private toaster: ToastrService) {
  }

  ngOnInit(): void {
    if (this.id) {
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
      this.genericService.saveData('settings/save-issue-type',obj)
        .subscribe(
          (data: any) => {
            console.log(data);
            if(data.code === 200){
              this.toaster.success(data.message);
              this.activeModal.close('success');
            }else {
              this.toaster.error(data.message);
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
