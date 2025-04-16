import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
@Component({
  selector: 'app-add-features',
  templateUrl: './add-features.component.html',
  styleUrls: ['./add-features.component.css']
})
export class AddFeaturesComponent {
  addForm: FormGroup = new FormGroup(
    {
      subCategory: new FormControl(''),
      model: new FormControl(''),
      icon: new FormControl(''),
      title: new FormControl('', Validators.required)
    }
  );
  categories: any;
  products: any;
  loading = true;
  iconList: any
  @Input() id: any;
  @Input() title: any;
  @Input() subCategory: any;
  @Input() icon: any;
  @Input() model: any;

  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    if (this.id !== '') {
      this.addForm.patchValue({
        title: this.title
      });
    }
    this.getAllCategory();
    this.getIconLibrary();
    this.getAllProduct();
  }

  getIconLibrary(): void {
    this.genericService.getData('settings/get-icon-library')
      .subscribe((data: any) => {
        this.iconList = data;
        if (this.id !== '') {
          this.addForm.patchValue({
            icon: this.icon
          });
        }
      },
        (error: any) => {

        });
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-sub-category')
      .subscribe((data: any) => {
        this.categories = data;
        this.loading = false;
        if (this.id !== '') {
          this.addForm.patchValue({
            subCategory: this.subCategory
          });
        }
      },
        (error: any) => {

        });
  }

  getAllProduct(): void {
    this.genericService.getData('product/get-product', true)
      .subscribe((data: any) => {
        this.products = data;
        this.loading = false;
        if (this.id !== '') {
          this.addForm.patchValue({
            model: this.model
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
        subCategory: undefined,
        model: undefined,
        icon: undefined,
      };

      if (this.id !== '') {
        obj.id = this.id;
      } else {
        delete obj.id;
      }
      obj.name = this.addForm.value.title;
      if (this.addForm.value.subCategory !== "") {
        obj.subCategory = this.addForm.value.subCategory;
      } else {
        delete obj.subCategory;
      }
      if (this.addForm.value.model !== "") {
        obj.model = this.addForm.value.model;
      } else {
        delete obj.model;
      }
      if (this.addForm.value.icon !== "") {
        obj.icon = this.addForm.value.icon;
      } else {
        delete obj.icon;
      }

      this.genericService.saveData('settings/save-feature', obj)
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
            this.toaster.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addForm);
    }
  }
}
