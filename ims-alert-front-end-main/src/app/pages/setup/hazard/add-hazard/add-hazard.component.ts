import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-hazard',
  templateUrl: './add-hazard.component.html',
  styleUrls: ['./add-hazard.component.css']
})
export class AddHazardComponent {
  addForm: any
  subCategories: any
  types: any
  loading: boolean = true;
  @Input() id: any;
  @Input() categoryId: any;
  @Input() code: any;
  @Input() type: any;
  @Input() description: any;
  @Input() symtoms: any;
  @Input() correctives: any;

  constructor(private toastr: ToastService,
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group(
      {
        subCategory: new FormControl('', Validators.required),
        type: new FormControl('', Validators.required),
        code: new FormControl('', Validators.required),
        description: new FormControl(''),
        symtoms: this.formBuilder.array([
          this.formBuilder.group(
            {
              id: '',
              symtom: ''
            })
        ]),
        correctives: this.formBuilder.array([
          this.formBuilder.group(
            {
              id: '',
              corrective: ''
            })
        ])
      }
    );
    if (this.id) {
      this.addForm.patchValue({
        code: this.code,
        type: this.type,
        description: this.description
      });
      for (let i = 0; i < this.symtoms?.length; i++) {
        if (i !== 0)
          this.addSymtom();
        this.getSymtoms.at(i)?.patchValue({
          id: this.symtoms[i].id,
          symtom: this.symtoms[i].symtom
        });
      }
      for (let i = 0; i < this.correctives?.length; i++) {
        if (i !== 0)
          this.addCorrective();
        this.getCorrective.at(i)?.patchValue({
          id: this.correctives[i].id,
          corrective: this.correctives[i].corrective
        });
      }
    }
    this.getAllSubCategory();
    this.getHazardTypes();
  }

  getAllSubCategory(): void {
    this.genericService.getData('settings/get-sub-category')
      .subscribe((data: any) => {
        this.subCategories = data;
        this.loading = false;
        if (this.categoryId) {
          this.addForm.patchValue({
            subCategory: this.categoryId
          });
        }
      },
        (error: any) => {

        });
  }

  getHazardTypes(): void {
    this.genericService.getData('settings/get-hazard-type')
      .subscribe((data: any) => {
        this.types = data;
        if (this.type) {
          this.addForm.patchValue({
            type: this.type
          });
        }
      },
        (error: any) => {

        });
  }

  get getSymtoms(): FormArray {
    return this.addForm.get('symtoms') as FormArray;
  }

  removeSymtom(symtomIndex: number, symtomId: any): void {
    if (this.getSymtoms.length > 1) {
      if (symtomId === '') {
        this.getSymtoms.removeAt(symtomIndex);
      } else {
        this.genericService.deleteData('settings/delete-symtom/' + symtomId)
          .subscribe((data) => {
            this.getSymtoms.removeAt(symtomIndex);
            this.toaster.success('Record Deleted Successfully !');
          }, (error: any) => {
            this.toaster.error('Failed To Delete Record !');
          });
      }
    }
  }

  addSymtom(): void {
    this.getSymtoms.push(this.formBuilder.group(
      {
        id: '',
        symtom: ''
      }
    ));
  }

  get getCorrective(): FormArray {
    return this.addForm.get('correctives') as FormArray;
  }

  removeCorrective(correctiveIndex: number, correctiveId: any): void {
    if (this.getCorrective.length > 1) {
      if (correctiveId === '') {
        this.getCorrective.removeAt(correctiveIndex);
      } else {
        this.genericService.deleteData('settings/delete-corrective/' + correctiveId)
          .subscribe((data) => {
            this.getCorrective.removeAt(correctiveIndex);
            this.toaster.success('Record Deleted Successfully !');
          }, (error: any) => {
            this.toaster.error('Failed To Delete Record !');
          });
      }
    }
  }

  addCorrective(): void {
    this.getCorrective.push(this.formBuilder.group(
      {
        id: '',
        corrective: ''
      }
    ));
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
        categoryId: undefined,
        code: undefined,
        type: undefined,
        description: undefined,
        symtoms: undefined,
        correctives: undefined
      };

      if (this.id) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }
      obj.categoryId = this.addForm.value.subCategory;
      obj.type = this.addForm.value.type;
      obj.code = this.addForm.value.code;
      obj.description = this.addForm.value.description;
      let symtoms: any = []
      this.addForm.value.symtoms.map((data: any, index: number) => {
        if (data.symtom.trim() !== '') {
          symtoms.push(data);
        }
      });
      if (symtoms.length > 0) {
        obj.symtoms = symtoms;
      } else {
        delete obj.symtoms;
      }
      let correctives: any = []
      this.addForm.value.correctives.map((data: any, index: number) => {
        if (data.corrective.trim() !== '') {
          correctives.push(data);
        }
      });
      if (correctives.length > 0) {
        obj.correctives = correctives;
      } else {
        delete obj.correctives;
      }
      console.log(obj);
      this.genericService.saveData('settings/save-hazard', obj)
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
