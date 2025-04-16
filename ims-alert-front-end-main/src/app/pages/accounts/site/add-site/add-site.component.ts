import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-site',
  templateUrl: './add-site.component.html',
  styleUrls: ['./add-site.component.css']
})
export class AddSiteComponent {
  addForm: FormGroup = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      country: new FormControl(''),
      state: new FormControl(''),
      city: new FormControl(''),
      address: new FormControl(''),
      phone: this.formBuilder.array([
        this.formBuilder.group(
          {
            number: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(12)]],
          })
      ]),
    }
  );
  loading = true;
  countries: any;
  cities: any;
  states:any;
  fileNme: any;
  fileSize: any;
  fileData: any;
  fileType: any;
  base64textString: any;

  @Input() id: any;
  @Input() editData: any;

  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService, private formBuilder: FormBuilder) {
  }

  get phoneNumbers(): FormArray {
    return this.addForm.get('phone') as FormArray;
  }

  addPhoneNumber(): void {
    this.phoneNumbers.push(this.formBuilder.group(
      {
        number: ['', Validators.compose([Validators.required, Validators.minLength(11), Validators.maxLength(12)])],
      }
    ));
  }

  removePhoneNumber(phoneGroup: number): void {
    if (phoneGroup > 0) {
      this.phoneNumbers.removeAt(phoneGroup);
    }
  }

  ngOnInit(): void {
    if (this.id) {
      this.addForm.patchValue({
        name: this.editData.name,
        address: this.editData.address,
      });
      console.log(this.editData);
      for (let i = 0; i < this.editData?.phone?.length; i++) {
        if (i !== 0)
          this.addPhoneNumber()
        this.phoneNumbers.at(i).patchValue({
          number: this.editData.phone[i].phoneNo
        });
      }
    }
    this.getAllCountry();
  }

  getAllCountry(): void {
    this.genericService.getData('settings/get-country')
      .subscribe((data: any) => {
        this.countries = data;
        this.loading = false;
        if (this.id) {
          this.addForm.patchValue({
            country: this.editData.country.id
          });
          this.getAllState(this.editData?.country?.id);
        }
      },
        (error: any) => {

        });
  }

  getAllState(countryId: any): void {
    this.states = [];
    if (countryId) {
      this.genericService.getData('settings/get-state-by-country/' + countryId)
        .subscribe((data: any) => {
          this.states = data?.response;
          if (this.id) {
            this.addForm.patchValue({
              state: this.editData.state.id
            });
            this.getAllCity(this.editData?.state?.id);
          }
        },
          (error: any) => {

          });
    }
  }

  getAllCity(stateId: any): void {
    this.cities = [];
    if (stateId) {
      this.genericService.getData('settings/get-city-by-state/' + stateId)
        .subscribe((data: any) => {
          this.cities = data?.response;
          if (this.id) {
            this.addForm.patchValue({
              city: this.editData?.city?.id
            });
          }
        },
          (error: any) => {

          });
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
        countryId: undefined,
        stateId: undefined,
        cityId: undefined,
        address: undefined,
        phone: undefined,
      };

      if (this.id) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }

      obj.name = this.addForm.value.name;
      obj.countryId = this.addForm.value.country;
      obj.stateId = this.addForm.value.state;
      obj.cityId = this.addForm.value.city;
      obj.address = this.addForm.value.address;
      let phones: any = []
      this.addForm.value.phone.map((data: any, index: number) => {
        phones.push(data.number);
      });
      if (phones.length > 0) {
        obj.phone = phones;
      } else {
        delete obj.phone;
      }
      this.genericService.saveData('right/save-site', obj)
        .subscribe(
          (data: any) => {
            if(data.code === 200){
              this.toaster.success(data.message);
              this.activeModal.close('success');
            }else {
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

  onlyInteger(e: any): void {
    let str1 = e.target.value;
    str1 = str1.replace(/[^\d]/g, '');
    e.target.value = str1;
  }
}
