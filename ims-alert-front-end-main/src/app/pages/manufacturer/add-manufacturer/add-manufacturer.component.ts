import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-manufacturer',
  templateUrl: './add-manufacturer.component.html',
  styleUrls: ['./add-manufacturer.component.css']
})
export class AddManufacturerComponent {
  addForm: FormGroup = new FormGroup(
    {
      category: new FormControl(''),
      name: new FormControl('', Validators.required),
      shortName: new FormControl('', Validators.required),
      country: new FormControl(''),
      city: new FormControl(''),
      address: new FormControl(''),
      landline: new FormControl(''),
      mobileNo: new FormControl('', Validators.required),
      weblink: new FormControl(''),
      introduceOn: new FormControl(''),
    }
  );
  categories: any;
  loading = true;
  countries: any;
  cities: any;
  fileNme: any;
  fileSize: any;
  fileData: any;
  fileType: any;
  base64textString: any;

  @Input() id: any;
  @Input() editData: any;

  constructor(private toastr: ToastService,
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  errorHandler(event: any) {
    event.target.src = "#";
  }

  ngOnInit(): void {
    console.log(this.editData);
    if (this.id) {
      this.addForm.patchValue({
        name: this.editData.name,
        shortName: this.editData.shortName,
        address: this.editData.address,
        landline: this.editData.landLine,
        mobileNo: this.editData.mobileNo,
        weblink: this.editData.webLink,
        introduceOn: this.editData.introduce,
      });
    }
    this.getAllCategory();
    this.getAllCountry();
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-category')
      .subscribe((data: any) => {
        this.categories = data;
        this.loading = false;
        if (this.id) {
          let products = new Array();
          this.editData.product.forEach((item: any) => {
            products.push(item?.category?.id);
          });
          this.addForm.patchValue({
            category: products
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
            country: this.editData.country.id
          });
          this.getAllCity(this.editData.country.id);
        }
      },
        (error: any) => {

        });
  }

  getAllCity(countryId: any): void {
    this.cities = [];
    if (countryId) {
      this.genericService.getData('settings/get-city-by-country/' + countryId)
        .subscribe((data: any) => {
          this.cities = data;
          if (this.id) {
            this.addForm.patchValue({
              city: this.editData.city.id
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
        product: undefined,
        name: undefined,
        shortName: undefined,
        countryId: undefined,
        cityId: undefined,
        address: undefined,
        landLine: undefined,
        mobileNo: undefined,
        webLink: undefined,
        introduce: undefined,
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
      obj.product = this.addForm.value.category;
      obj.name = this.addForm.value.name;
      obj.shortName = this.addForm.value.shortName;
      obj.countryId = this.addForm.value.country;
      obj.cityId = this.addForm.value.city;
      obj.address = this.addForm.value.address;
      obj.landLine = this.addForm.value.landline;
      obj.mobileNo = this.addForm.value.mobileNo;
      obj.webLink = this.addForm.value.weblink;
      obj.introduce = this.addForm.value.introduce;
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
      this.genericService.saveData('settings/save-manufacturer', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.activeModal.close('success');
            } else {
              this.toaster.error(data.message);
            }

          },
          (error: any) => {
            this.toastr.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addForm);
    }
  }
}
