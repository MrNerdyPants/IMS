import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from '../../toast.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-maintenance',
  templateUrl: './add-maintenance.component.html',
  styleUrls: ['./add-maintenance.component.css']
})
export class AddMaintenanceComponent {
  saleDate: string = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  addForm: FormGroup = new FormGroup(
    {
      date: new FormControl(this.saleDate, Validators.required),
      customer: new FormControl('', Validators.required),
      product: new FormControl('', Validators.required),
      // employee: new FormControl('', Validators.required),
      detail: this.formBuilder.array([
        this.formBuilder.group(
          {
            checklistId: ['', Validators.required],
            productType: ['', Validators.required],
            title: ['', Validators.required],
            value: ['', Validators.required]
          }
        )
      ])
    }
  );
  customers: any;
  loading = true;
  products: any;
  id: any;
  editData: any;
  employees: any;
  checkList: any;
  issueType: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllData();
    this.getAllEmployee();
    this.getAllCustomerCheckList();
    this.getAllIssueType();
    if (this.route.snapshot.params['id']) {
      this.id = this.route.snapshot.params['id'];
      this.getSalesById(this.route.snapshot.params['id']);
    }

  }

  getAllIssueType(): void {
    this.genericService.getData('settings/get-issue-type', true)
      .subscribe((data: any) => {
        this.issueType = data;
        console.log(data);
      },
        (error: any) => {

        });
  }

  getSalesById(id: any) {
    this.genericService.getData('business/get-sale/' + id)
      .subscribe((data: any) => {
        this.editData = data?.response;
        console.log(data?.response);
        this.addForm.patchValue({
          date: this.editData.date,
          customer: this.editData.customer,
          employee: this.editData.employee
        });
        for (let i = 0; i < this.editData.detail?.length; i++) {
          if (i !== 0)
            this.addDetail();
          this.getDetail.at(i)?.patchValue({
            product: this.editData.detail[i].product,
            serial: this.editData.detail[i].serial
          });
        }
      },
        (error: any) => {

        });
  }

  getAllProduct(id: any): void {
    if (id !== '') {
      this.genericService.getData('business/get-product-by-customer/' + id)
        .subscribe((data: any) => {
          this.products = data;
        },
          (error: any) => {

          });
    } else {
      this.products = [];
    }
  }

  getAllData(): void {
    this.genericService.getData('settings/get-customer', true)
      .subscribe((data: any) => {
        this.customers = data;
        this.loading = false;
      },
        (error: any) => {

        });
  }

  getAllEmployee(): void {
    this.genericService.getData('right/get-employee')
      .subscribe((data: any) => {
        this.employees = data;
        this.loading = false;
      },
        (error: any) => {

        });
  }

  // CheckListType == Customer == 10 

  getAllCustomerCheckList(): void {
    this.genericService.getData('settings/get-check-list-by-type/10', true)
      .subscribe((data: any) => {
        this.checkList = data.response;
        let index = 0;
        console.log(this.checkList);
        for (let i = 0; i < this.checkList.length; i++) {
          for (let j = 0; j < this.checkList[i]?.list?.length; j++) {
            if (index !== 0)
              this.addDetail();
            this.getDetail.at(index)?.patchValue({
              id: '',
              checklistId: this.checkList[i].id,
              productType: this.checkList[i]?.issueType?.id,
              title: this.checkList[i]?.list[j]?.title,
              value: "N"
            });
            index++;
          }
        }
      },
        (error: any) => {

        });
  }

  get getDetail(): FormArray {
    return this.addForm.get('detail') as FormArray;
  }

  addDetail(): void {
    this.getDetail.push(this.formBuilder.group(
      {
        id: [''],
        checklistId: ['', Validators.required],
        productType: ['', Validators.required],
        title: ['', Validators.required],
        value: ['', Validators.required],
      }
    ));
  }

  removeDetail(index: number): void {
    if (this.getDetail.length > 1) {
      this.getDetail.removeAt(index);
    }
  }

  onSubmit() {
    if (this.addForm.valid) {
      const obj = {
        id: undefined,
        customer: undefined,
        employee: undefined,
        product: undefined,
        status: "un-assigned",
        date: undefined,
        detail: undefined
      };
      this.id ? obj.id = this.id : delete obj.id;
      
      obj.date = this.addForm.value.date;
      obj.customer = this.addForm.value.customer;
      obj.product = this.addForm.value.product;
      obj.detail = this.addForm.value.detail;

      this.genericService.saveData('maintenance/save-maintenance', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.router.navigate(['/task-assignment']);
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
}
