import { Component, Input } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-province',
  templateUrl: './add-province.component.html',
  styleUrls: ['./add-province.component.scss']
})
export class AddProvinceComponent {

  addForm: any;
  categories: any;
  loading = false;
  countries: any;
  selectedCountry: any = "";

  @Input() id: any;
  @Input() countryId: any;
  @Input() title: any;
  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    this.addForm = new FormGroup({
      title: new FormControl('', Validators.required),
      countryId: new FormControl('', Validators.required),
      activeStatus: new FormControl(true)
    });
    this.getCountrys();

    if (this.title) {
      this.addForm.controls.title.value = this.title;
    }
  }


  getCountrys(): void {
    this.genericService.getData('settings/get-country')
      .subscribe((data: any) => {
        this.countries = data;
        if (this.countryId) {
          this.selectedCountry = this.countryId;
        }
      }, (error: any) => {
        this.toaster.error('Error in loading Countrys...')
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
        id: undefined,
        name: undefined,
        countryId: undefined
      };
      obj.name = this.addForm.value.title;
      obj.countryId = this.selectedCountry;
      obj.id = this.id !== 'undefined' ? this.id : undefined;
      this.genericService.saveData('settings/save-state', obj)
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
