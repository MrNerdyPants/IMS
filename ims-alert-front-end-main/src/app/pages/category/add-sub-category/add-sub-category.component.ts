import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-sub-category',
  templateUrl: './add-sub-category.component.html',
  styleUrls: ['./add-sub-category.component.scss']
})
export class AddSubCategoryComponent {
  addForm: any;
  categories: any;
  loading = true;

  @Input() id: any;
  @Input() title: any;
  @Input() parentId: any;
  @Input() activeStatus: any;
  @Input() types: any;
  constructor(private toastr: ToastService,
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.addForm = new FormGroup(
      {
        parentId: new FormControl('', Validators.required),
        title: new FormControl('', Validators.required),
        activeStatus: new FormControl(true),
        productType: this.formBuilder.array([
          this.formBuilder.group(
            {
              id: '',
              title: '',
              code: ''
            })
        ]),
      }
    );
    if (this.parentId) {
      this.addForm.patchValue({
        title: this.title,
        activeStatus: this.activeStatus === 'Y' ? true : false
      });
      console.log(this.types);
      for (let i = 0; i < this.types?.length; i++) {
        if (i !== 0)
          this.addProductType();
        this.getProductType.at(i)?.patchValue({
          id: this.types[i].id,
          title: this.types[i].title,
          code: this.types[i].code
        });
      }
    }
    this.getAllCategory();
  }

  get getProductType(): FormArray {
    return this.addForm.get('productType') as FormArray;
  }

  addProductType(): void {
    this.getProductType.push(this.formBuilder.group(
      {
        id: '',
        title: '',
        code: ''
      }
    ));
  }
  removeProductType(index: number, productTypeId: any): void {
    if (this.getProductType.length > 1) {
      if (productTypeId === '') {
        this.getProductType.removeAt(index);
      } else {
        this.genericService.deleteData('settings/delete-product-type/' + productTypeId)
          .subscribe((data) => {
            this.getProductType.removeAt(index);
            this.toaster.success('Record Deleted Successfully !');
          }, (error: any) => {
            this.toaster.error('Failed To Delete Record !');
          });
      }
    }
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-category')
      .subscribe((data: any) => {
        this.categories = data;
        this.loading = false;
        if (this.parentId) {
          this.addForm.patchValue({
            parentId: this.parentId
          });
        }
      },
        (error: any) => {

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
        parentId: undefined,
        types: undefined,
        activeInd: ''
      };

      if (this.parentId) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }

      obj.name = this.addForm.value.title;
      obj.parentId = this.addForm.value.parentId;
      obj.activeInd = this.addForm.value.activeStatus === true ? 'Y' : 'N';
      let productTypes: any = []
      this.addForm.value.productType.map((data: any, index: number) => {
        if (data.title.trim() !== '') {
          productTypes.push(data);
        }
      });
      if (productTypes.length > 0) {
        obj.types = productTypes;
      } else {
        delete obj.types;
      }
      this.genericService.saveData('settings/save-category', obj)
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
