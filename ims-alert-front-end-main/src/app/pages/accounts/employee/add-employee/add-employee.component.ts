import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {
  addForm: FormGroup = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      department: new FormControl(''),
      designation: new FormControl(''),
      phone: this.formBuilder.array([
        this.formBuilder.group(
          {
            number: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(12)]],
          })
      ]),
      email: new FormControl(''),
      site: new FormControl(''),
      loginId: new FormControl(''),
      loginPassword: new FormControl(''),
    }
  );
  loading = true;
  departments:any;
  sites:any;
  designations:any;
  @Input() id: any;
  @Input() editData: any;

  constructor(private toastr: ToastService,
    public activeModal: NgbActiveModal, public genericService: GenericService,
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
        email: this.editData.email,
        department: this.editData.department?.id,
        designation: this.editData.designation?.id,
      });
      for (let i = 0; i < this.editData?.phone?.length; i++) {
        if (i !== 0)
          this.addPhoneNumber()
        this.phoneNumbers.at(i).patchValue({
          number: this.editData.phone[i].phoneNo
        });
      }
    }
    this.getAllDesignation();
    this.getAllDepartment();
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

  getAllDepartment(): void {
    this.genericService.getData('settings/get-department')
      .subscribe((data: any) => {
        this.departments = data;
        this.loading =false;
      },
        (error: any) => {
          this.loading =false;
        });
  }

  getAllDesignation(): void {
    this.genericService.getData('settings/get-designation')
      .subscribe((data: any) => {
        this.designations = data;
        this.loading =false;
      },
        (error: any) => {
          this.loading =false;
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
        department: undefined,
        designation: undefined,
        phone: undefined,
        email: undefined,
        siteId: undefined,
        loginId: undefined,
        loginPassword: undefined
      };

      if (this.id) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }

      obj.name = this.addForm.value.name;
      obj.department = this.addForm.value.department;
      obj.siteId = this.addForm.value.site;
      obj.designation = this.addForm.value.designation;
      let phones: any = []
      this.addForm.value.phone.map((data: any, index: number) => {
        phones.push(data.number);
      });
      if (phones.length > 0) {
        obj.phone = phones;
      } else {
        delete obj.phone;
      }
      obj.email = this.addForm.value.email;
      obj.loginId = this.addForm.value.loginId;
      obj.loginPassword = this.addForm.value.loginPassword;
      this.genericService.saveData('right/save-employee', obj)
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
            if(error?.error?.code === 409){
              this.toastr.error(error?.error?.response?.message);
            }else {
              this.toastr.error(error?.error?.message);
            }
            
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
