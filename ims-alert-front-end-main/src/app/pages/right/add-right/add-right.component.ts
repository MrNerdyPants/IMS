import { Component, Input } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-right',
  templateUrl: './add-right.component.html',
  styleUrls: ['./add-right.component.scss']
})
export class AddRightComponent {
  addRight: any;
  @Input() id: any;
  @Input() title: any;
  @Input() parent: any;
  @Input() sort: any;
  @Input() url: any;
  @Input() levelNbr: any;
  @Input() activeInd: any;
  @Input() displayInd: any;
  allParents: any;

  constructor(private toastr: ToastService, private formBuilder: FormBuilder,
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    this.addRight = this.formBuilder.group({
      title: new FormControl('', Validators.required),
      parent: new FormControl(''),
      sort: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      activeInd: true,
      displayInd: true,
    });
    if (this.id !== '') {
      console.log(this.parent);
      this.addRight.patchValue({
        title: this.title,
        activeInd: this.activeInd === 'Y' ? true : false,
        sort: this.sort,
        parent: this.parent,
        url: this.url,
        displayInd: this.displayInd === 'Y' ? true : false
      });
    }
    this.getAllParents();
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

  onRightSubmit(): void {
    if (this.addRight.valid) {
      const right = {
        id: undefined,
        name: undefined,
        sort: undefined,
        url: undefined,
        parentId: undefined,
        activeInd: '',
        displayInd: ''
      };
      this.id !== '' ? right.id = this.id : delete right.id;
      right.name = this.addRight.value.title;
      right.sort = this.addRight.value.sort;
      right.url = this.addRight.value.url;
      this.addRight.value.parent !== '' ? right.parentId = this.addRight.value.parent : delete right.parentId;
      right.activeInd = this.addRight.value.activeInd === true ? 'Y' : 'N';
      right.displayInd = this.addRight.value.displayInd === true ? 'Y' : 'N';
      this.genericService.saveData('right/save-right', right)
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
            this.toastr.error('Unexpected Error! Please check you network');
          });
    } else {
      this.validateAllFormFields(this.addRight);
    }

  }

  getAllParents(): void {
    this.genericService.getData('right/get-parent-rights')
      .subscribe(
        (data: any) => {
          this.allParents = data;
        },
        (error: any) => {
        });
  }

  getParentRightSortNbr(id: any): void {
    this.genericService.getData('right/get-right-child-nbr/' + id)
      .subscribe(
        (data: any) => {
          this.addRight.controls.sort.patchValue(data?.response);
        },
        (error: any) => {
        });
  }
}
