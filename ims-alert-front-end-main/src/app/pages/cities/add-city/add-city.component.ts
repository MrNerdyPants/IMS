import { Component, Input } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-add-city',
  templateUrl: './add-city.component.html',
  styleUrls: ['./add-city.component.scss']
})
export class AddCityComponent {
  addForm: any;
  categories: any;
  loading = false;
  states: any;
  countries: any;
  selectedCountry: any = "";
  selectedState: any = "";
  holdStatePreview: any = "";

  @Input() id: any;
  @Input() stateId: any;
  @Input() countryId: any;
  @Input() title: any;
  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    this.addForm = new FormGroup({
      title: new FormControl('', Validators.required),
      stateId: new FormControl('', Validators.required),
      countryId: new FormControl('', Validators.required),
      activeStatus: new FormControl(true)
    });
    this.getCountrys();
    if (this.title) {
      this.addForm.controls.title.value = this.title;
    }

    if (this.stateId) {
      this.holdStatePreview = this.stateId;
    }
  }


  getCountrys(): void {
    this.genericService.getData('settings/get-country')
      .subscribe((data: any) => {
        this.countries = data;
        if (this.countryId) {
          this.selectedCountry = this.countryId;
          this.getProvinceByCountryId();
        }
      }, (error: any) => {
        this.toaster.error('Error in loading Countrys...')
      });
  }

  getProvinceByCountryId(): void {
    this.selectedState = "";
    if (this.selectedCountry) {
      this.genericService.getData('settings/get-state-by-country/' + this.selectedCountry)
        .subscribe((data) => {
          this.states = data;
          this.states?.response.filter((item: any) => {
            if (item.id == this.holdStatePreview) {
              this.selectedState = this.holdStatePreview;
            }
          })
        }, (error) => {

        })
    } else {
      this.states = [];
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
        name: undefined,
        stateId: undefined,
        countryId: undefined
      };
      obj.name = this.addForm.value.title;
      obj.countryId = this.selectedCountry;
      obj.stateId = this.selectedState !== '' ? this.selectedState : this.holdStatePreview;
      obj.id = this.id !== 'undefined' ? this.id : undefined;
      this.genericService.saveData('settings/save-city', obj)
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
