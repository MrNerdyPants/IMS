import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-customer-check-list',
  templateUrl: './add-customer-check-list.component.html',
  styleUrls: ['./add-customer-check-list.component.css']
})
export class AddCustomerCheckListComponent {
  addStatus: FormGroup = new FormGroup(
    {
      titles: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: [''],
            title: ['', [Validators.required]]
          })
      ]),
      type: new FormControl('', Validators.required),
      productType: new FormControl('', Validators.required),
      checkListCategory: new FormControl(''),
      category: new FormControl(''),
      activeStatus: new FormControl(true)
    }
  );
  categories: any;
  checkListCategories: any;
  issueType: any;
  loading = true;
  types: any;

  @Input() id: any;
  @Input() titles: any;
  @Input() activeStatus: any;
  @Input() typeId: any;
  @Input() categoryId: any;
  @Input() productType: any;
  @Input() checkListCategoryId: any

  constructor(public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    if (this.id) {
      this.addStatus.patchValue({
        type: this.typeId,
        productType: this.productType,
        activeStatus: this.activeStatus === 'Y' ? true : false
      });
      for (let i = 0; i < this.titles?.length; i++) {
        if (i !== 0)
          this.addTitle()
        this.getTitles.at(i).patchValue({
          id: this.titles[i].id,
          title: this.titles[i].title
        });
      }
    }
    this.getAllCategory();
    this.getAllIssueType();
    this.getCheckListCategory();
    this.getCheckListTypes();
  }

  getAllIssueType(): void {
    this.genericService.getData('settings/get-issue-type', true)
      .subscribe((data: any) => {
        this.issueType = data;
        this.addStatus.patchValue({
          productType: this.productType
        })
      }, (error: any) => {

      });
  }

  get getTitles(): FormArray {
    return this.addStatus.get('titles') as FormArray;
  }

  addTitle(): void {
    this.getTitles.push(this.formBuilder.group(
      {
        id: '',
        title: ['', Validators.compose([Validators.required])]
      }
    ));
  }

  removeTitle(index: number): void {
    if (index > 0) {
      this.getTitles.removeAt(index);
    }
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-all-category')
      .subscribe((data: any) => {
        this.categories = data;
        this.loading = false;
        if (this.id) {
          this.addStatus.patchValue({
            category: this.categoryId
          });
        }
      },
        (error: any) => {

        });
  }

  getCheckListTypes(): void {
    this.genericService.getData('settings/get-checklist-type')
      .subscribe((data: any) => {
        this.types = data;
        if (this.id) {
          this.addStatus.patchValue({
            type: this.typeId
          });
        }
      },
        (error: any) => {

        });
  }


  getCheckListCategory(): void {
    this.genericService.getData('settings/get-checklist-category')
      .subscribe((data: any) => {
        this.checkListCategories = data;
        this.loading = false;
        if (this.id) {
          this.addStatus.patchValue({
            checkListCategory: this.checkListCategoryId
          });
        }
        console.log(data);
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
    if (this.addStatus.valid) {
      const obj = {
        id: undefined,
        list: undefined,
        type: undefined,
        checkListCategoryId: undefined,
        categoryId: undefined,
        productType: undefined,
        activeInd: ''
      };

      if (this.id) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }
      let titleList: any = []
      this.addStatus.value.titles.map((data: any, index: number) => {
        titleList.push(data);
      });
      obj.list = titleList;
      obj.type = this.addStatus.value.type;
      obj.productType = this.addStatus.value.productType;
      obj.checkListCategoryId = this.addStatus.value.checkListCategory;
      obj.categoryId = this.addStatus.value.category;
      obj.activeInd = this.addStatus.value.activeStatus === true ? 'Y' : 'N';
      console.log(obj);
      this.genericService.saveData('settings/save-customer-check-list', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.activeModal.close('success');
            } else {
              this.toaster.error(data.message);
              this.activeModal.close('success');
            }

          },
          (error: any) => {
            this.toaster.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addStatus);
    }
  }
}
