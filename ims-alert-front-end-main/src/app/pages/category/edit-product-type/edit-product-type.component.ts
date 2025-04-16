import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { title } from 'process';
import { ToastService } from 'src/app/pages/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-edit-product-type',
  templateUrl: './edit-product-type.component.html',
  styleUrls: ['./edit-product-type.component.scss']
})
export class EditProductTypeComponent implements OnInit {
  addProductTypeForm: any;
  categories: any;
  subCategories: any;
  loading = true;
  productTypeNatures: Array<any> = [{ title: 'Assets' }, { title: 'Sale' }];

  @Input() productType: any;
  @Input() subCategory: any;
  constructor(private toastr: ToastService,
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService, private formBuilder: FormBuilder) {
  }
  ngOnInit(): void {
    this.addProductTypeForm = new FormGroup(
      {
        title: new FormControl('', Validators.required),
        typeNature: new FormControl('', Validators.required),
        code: new FormControl('', Validators.required)
      }
    );

    if (this.productType?.id) {
      if (this.subCategory?.id) {
        console.log(this.subCategory, this.productType)
        this.addProductTypeForm.patchValue({
          title: this.productType?.title,
          code: this.productType?.code,
          typeNature: this.productType?.nature,
        })
      }
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
    if (this.addProductTypeForm.valid) {
      const obj = {
        id: undefined,
        title: undefined,
        code: undefined,
        typeNature: undefined,
        categoryId: undefined
      };

      if (this.productType?.id) {
        obj.id = this.productType?.id;
      } else {
        delete obj.id;
      }

      obj.title = this.addProductTypeForm.value.title;
      obj.code = this.addProductTypeForm.value.code;
      obj.typeNature = this.addProductTypeForm.value.typeNature;
      obj.categoryId = this.subCategory?.id;
      this.genericService.saveData('settings/save-productType', obj)
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
      this.validateAllFormFields(this.addProductTypeForm);
    }
  }
}
