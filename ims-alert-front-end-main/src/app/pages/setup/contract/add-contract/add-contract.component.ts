import { formatDate } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from 'src/app/pages/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-add-contract',
  templateUrl: './add-contract.component.html',
  styleUrls: ['./add-contract.component.css']
})
export class AddContractComponent {
  contractDate: string = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  contractExpiry: string = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  addForm: FormGroup = new FormGroup(
    {
      customer: new FormControl('', Validators.required),
      period: new FormControl('', Validators.required),
      date: new FormControl(this.contractDate, Validators.required),
      expiry: new FormControl(this.contractExpiry, Validators.required),
      detail: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: [''],
            type: ['', Validators.required],
            paymentTerm: ['', Validators.required],
            noOfVisits: [''],
            amount: ['', Validators.required]
          }
        )
      ])
    }
  );
  customers: any;
  loading = true;
  id: any;
  editData: any;
  maintananceTypes = [
    {
      "name": "Installation or Commissioning"
    },
    {
      "name": "Contract Visits"
    },
    {
      "name": "Emergency Visits"
    },
    {
      "name": "Maintenance Visits"
    },
    {
      "name": "Training Visits"
    },
    {
      "name": "On Call"
    }
  ];

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllData();
    if (this.route.snapshot.params['id'] && this.route.snapshot.params['id'] !== ':id') {
      this.id = this.route.snapshot.params['id'];
      this.getContractById(this.route.snapshot.params['id']);
    }
  }

  getContractById(id: any) {
    this.genericService.getData('business/get-contract-by-id/' + id)
      .subscribe((data: any) => {
        console.log(data);
        this.editData = data?.response;
        this.addForm.patchValue({
          date: formatDate(new Date(this.editData.date), 'yyyy-MM-dd', 'en'),
          expiry: formatDate(new Date(this.editData.expiry), 'yyyy-MM-dd', 'en'),
          customer: this.editData.customer?.id,
          period: this.editData.period
        });
        for (let i = 0; i < this.editData.detail?.length; i++) {
          if (i !== 0)
            this.addDetail();
          this.getDetail.at(i)?.patchValue({
            id: this.editData.detail[i].id,
            type: this.editData.detail[i].type,
            paymentTerm: this.editData.detail[i].paymentTerm,
            noOfVisits:this.editData.detail[i].noOfVisits,
            amount: this.editData.detail[i].amount,
          });
        }
      }, (error: any) => {

      });
  }

  getAllData(): void {
    this.genericService.getData('settings/get-customer')
      .subscribe((data: any) => {
        this.customers = data;
        this.loading = false;
      }, (error: any) => {

      });
  }

  get getDetail(): FormArray {
    return this.addForm.get('detail') as FormArray;
  }

  addDetail(): void {
    this.getDetail.push(this.formBuilder.group(
      {
        id: [''],
        type: ['', Validators.required],
        paymentTerm: ['', Validators.required],
        noOfVisits:[''],
        amount: ['', Validators.required],
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
        period: undefined,
        date: undefined,
        expiry: undefined,
        detail: undefined
      };
      this.id ? obj.id = this.id : delete obj.id;

      obj.date = this.addForm.value.date;
      obj.expiry = this.addForm.value.expiry;
      obj.customer = this.addForm.value.customer;
      obj.period = this.addForm.value.period;
      obj.detail = this.addForm.value.detail;

      this.genericService.saveData('business/save-contract', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.router.navigate(['/contracts']);
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
