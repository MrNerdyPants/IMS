import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from 'src/app/pages/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent {
  addForm: FormGroup = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      shortName: new FormControl('', Validators.required),
      category: new FormControl(''),
      country: new FormControl(''),
      state: new FormControl(''),
      city: new FormControl(''),
      site: new FormControl(''),
      address: new FormControl(''),
      officeAddress: new FormControl(''),
      landline: new FormControl(''),
      phone: this.formBuilder.array([
        this.formBuilder.group(
          {
            number: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(12)]],
          })
      ]),
      authorizedPerson: new FormControl(''),
      email: new FormControl(''),
      ntnNumber: new FormControl(''),
      gstNumber: new FormControl(''),
      secpNumber: new FormControl(''),
      // serivceContract: new FormControl('N'),
      // startDate: new FormControl(''),
      // endDate: new FormControl(''),
      // period: new FormControl(''),
      // amount: new FormControl(''),
      loginId: new FormControl(''),
      loginPassword: new FormControl(''),
    }
  );
  categories: any;
  loading = true;
  countries: any;
  cities: any;
  states:any;
  sites:any;
  fileNme: any;
  fileSize: any;
  fileData: any;
  fileType: any;
  base64textString: any;

  @Input() id: any;
  @Input() editData: any;
  serivceContract = false;

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

  errorHandler(event: any) {
    event.target.src = "#";
  }

  ngOnInit(): void {
    if (this.id) {
      console.log(this.editData);
      this.addForm.patchValue({
        name: this.editData.name,
        shortName: this.editData.shortName,
        address: this.editData.address,
        officeAddress: this.editData.officeAddress,
        landline: this.editData.landLine,
        authorizedPerson: this.editData.authorizedPerson,
        email: this.editData.email,
        ntnNumber: this.editData.ntnNumber,
        gstNumber: this.editData.gstNumber,
        secpNumber: this.editData.secpNumber,
        serivceContract: this.editData.serivceContract,
        startDate: this.editData.startDate,
        endDate: this.editData.endDate,
        period: this.editData.period,
        amount: this.editData.amount,
      });
      this.serivceContract = (this.editData.serivceContract === 'Y' ? true : false);
      for (let i = 0; i < this.editData.phone.length; i++) {
        if (i !== 0)
          this.addPhoneNumber()
        this.phoneNumbers.at(i).patchValue({
          number: this.editData.phone[i]?.phoneNo
        });
      }
    }
    this.getAllCategory();
    this.getAllCountry();
    this.getAllSitesList();
  }

  getAllSitesList(): void {
    this.genericService.getData('right/get-site',true)
      .subscribe(
        (        data: any) => {
          this.sites = data;
          if (this.id) {
            this.addForm.patchValue({
              site: this.editData.site?.id
            });
          }
        },
        (        error: any) => {
        });
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-all-category')
      .subscribe((data: any) => {
        this.categories = data;
        this.loading = false;
        if (this.id) {
          let editBusiness = new Array();
          this.editData?.category?.forEach((element:any) => {
            editBusiness.push(element?.category?.id);
          });
          this.addForm.patchValue({
            category: editBusiness
          });
        }
      },
        (error: any) => {

        });
  }

  getAllCountry(): void {
    this.genericService.getData('settings/get-country')
      .subscribe((data: any) => {
        this.countries = data;
        this.loading = false;
        if (this.id) {
          this.addForm.patchValue({
            country: this.editData?.country?.id
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
              state: this.editData?.state?.id
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

  onFileSelect(event: any): void {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      if (file.size > 524288) {
        this.toaster.error('File Size cannot exceed 500 KB Limit.');
        file = '';
        this.fileNme = null;
        this.fileSize = null;
        this.fileType = null;
      } else {
        this.fileNme = file.name;
        this.fileSize = file.size;
        this.fileType = file.type;
      }
      const fileReader = new FileReader();
      fileReader.onload = this.handleFile.bind(this);
      fileReader.readAsBinaryString(file);
      event.target.value = null;
    }
  }

  handleFile(event: any): void {
    const binaryString = event.target.result;
    this.base64textString = btoa(binaryString);
    this.fileData = btoa(binaryString);
  }

  onSubmit(): void {
    if (this.addForm.valid) {
      const obj = {
        id: undefined,
        shortName: undefined,
        name: undefined,
        category: undefined,
        countryId: undefined,
        stateId: undefined,
        cityId: undefined,
        siteId: undefined,
        address: undefined,
        officeAddress: undefined,
        landLine: undefined,
        phone: undefined,
        authorizedPerson: undefined,
        email: undefined,
        ntnNumber: undefined,
        gstNumber: undefined,
        secpNumber: undefined,
        // serivceContract: undefined,
        // startDate: undefined,
        // endDate: undefined,
        // period: undefined,
        // amount: undefined,
        loginId: undefined,
        loginPassword: undefined,
        logo: undefined,
        fileNme: undefined,
        fileSize: undefined,
        fileType: undefined,
      };

      if (this.id) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }

      obj.name = this.addForm.value.name;
      obj.shortName = this.addForm.value.shortName;
      obj.category = this.addForm.value.category;
      obj.countryId = this.addForm.value.country;
      obj.stateId = this.addForm.value.state;
      obj.cityId = this.addForm.value.city;
      obj.siteId = this.addForm.value.site;
      obj.address = this.addForm.value.address;
      obj.officeAddress = this.addForm.value.officeAddress;
      obj.landLine = this.addForm.value.landline;
      let phones: any = []
      this.addForm.value.phone.map((data: any, index: number) => {
        phones.push(data.number);
      });
      if (phones.length > 0) {
        obj.phone = phones;
      } else {
        delete obj.phone;
      }
      obj.authorizedPerson = this.addForm.value.authorizedPerson;
      obj.email = this.addForm.value.email;
      obj.ntnNumber = this.addForm.value.ntnNumber;
      obj.gstNumber = this.addForm.value.gstNumber;
      obj.secpNumber = this.addForm.value.secpNumber;
      // obj.serivceContract = this.addForm.value.serivceContract;
      obj.loginId = this.addForm.value.loginId;
      obj.loginPassword = this.addForm.value.loginPassword;
      if (this.fileData) {
        obj.logo = this.fileData;
        obj.fileNme = this.fileNme;
        obj.fileSize = this.fileSize;
        obj.fileType = this.fileType;
      } else {
        delete obj.logo;
        delete obj.fileNme;
        delete obj.fileSize;
        delete obj.fileType;
      }
      // if(this.addForm.value.serivceContract === 'Y'){
      //   obj.startDate = this.addForm.value.startDate;
      //   obj.endDate = this.addForm.value.endDate;
      //   obj.period = this.addForm.value.period;
      //   obj.amount = this.addForm.value.amount;
      // }else {
      //   delete obj.startDate;
      //   delete obj.endDate;
      //   delete obj.period;
      //   delete obj.amount;
      // }
      this.genericService.saveData('settings/save-customer', obj)
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

  changeService(val:any){
    console.log(val);
    this.serivceContract = (val.target.value === 'Y' ? true : false);
  }

  onlyInteger(e: any): void {
    let str1 = e.target.value;
    str1 = str1.replace(/[^\d]/g, '');
    e.target.value = str1;
  }
}
