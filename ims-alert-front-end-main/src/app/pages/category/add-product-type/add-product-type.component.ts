import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from 'src/app/pages/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-add-product-type',
  templateUrl: './add-product-type.component.html',
  styleUrls: ['./add-product-type.component.scss']
})
export class AddProductTypeComponent {
  addForm: any;
  categories: any;
  subCategories: any;
  loading = true;
  productTypeNatures: Array<any> = [{ title: 'Assets' }, { title: 'Sale' }];
  @Input() id: any;
  @Input() categoryId: any;
  @Input() subCategoryId: any;
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
        childId: new FormControl('', Validators.required),
        activeStatus: new FormControl(true),
        productType: this.formBuilder.array([
          this.formBuilder.group(
            {
              id: '',
              title: '',
              code: '',
              typeNature: ''
            })
        ]),
      }
    );
    if (this.categoryId) {
      this.addForm.patchValue({
        childId: this.subCategoryId,
        activeStatus: this.activeStatus === 'Y' ? true : false
      });

      for (let i = 0; i < this.types?.length; i++) {
        if (i !== 0)
          this.addProductType();
        this.getProductType.at(i)?.patchValue({
          id: this.types[i].id,
          title: this.types[i].title,
          code: this.types[i].code,
          typeNature: this.types[i].typeNature
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
        code: '',
        typeNature: ''
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
        if (this.categoryId) {
          this.addForm.patchValue({
            parentId: this.categoryId
          });
        }
      },
        (error: any) => {

        });
  }

  getAllSubCategory(categoryId: any): void {
    console.log(categoryId);
    this.genericService.getData('settings/get-sub-category-by-parent/' + categoryId)
      .subscribe((data: any) => {
        this.subCategories = data;
        this.loading = false;
        console.log(data);
        if (this.subCategoryId) {
          this.addForm.patchValue({
            childId: this.subCategoryId
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
        id: this.addForm.value.childId,
        types: undefined
      };
      
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
      this.genericService.saveData('settings/save-category-product-types', obj)
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
