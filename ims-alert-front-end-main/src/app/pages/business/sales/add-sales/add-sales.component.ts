import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/pages/toast.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-sales',
  templateUrl: './add-sales.component.html',
  styleUrls: ['./add-sales.component.css']
})
export class AddSalesComponent {

  saleDate: string = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  addForm: any;
  customers: any;
  loading = true;
  products: any;
  productTypes: any;
  id: any;
  editData: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // console.log(this.saleDate);
    this.addForm = new FormGroup(
      {
        date: new FormControl(this.saleDate, Validators.required),
        customer: new FormControl('', Validators.required),
        detail: this.formBuilder.array([
          this.formBuilder.group(
            {
              id: '',
              productType: ['', Validators.required],
              productList: [],
              model: ['', Validators.required],
              product: ['', Validators.required],
              serial: ['', Validators.required],
              referenceNo: [''],
              mudulerSerial: this.formBuilder.array([])
            }
          )
        ])
      }
    );
    this.getAllData();
    this.getAllProductType();
    if (this.route.snapshot.params['id'] !== ':id') {
      this.id = this.route.snapshot.params['id'];
      this.getSalesById(this.route.snapshot.params['id']);
    }
  }

  getSalesById(id: any) {
    this.genericService.getData('business/get-sale/' + id)
      .subscribe((data: any) => {
        // console.log(data);
        this.editData = data?.response;
        // console.log(this.editData);
        this.addForm.patchValue({
          date: (formatDate(new Date(this.editData.date), 'yyyy-MM-dd', 'en')),
          customer: this.editData.customer?.id
        });
        for (let i = 0; i < this.editData.detail?.length; i++) {
          if (i !== 0)
            this.addDetail();
          for (let muduler of this.editData.detail[i]?.mudulerSerial) {
            this.getMuduler(i).push(this.formBuilder.group(
              {
                id: [muduler.id],
                name: [muduler.name, Validators.required],
                serial: [muduler.serial, Validators.required],
              }
            ));
          }
          this.getProductsByType(this.editData.detail[i].productType?.id, i);
          this.getDetail.at(i)?.patchValue({
            id: this.editData.detail[i].id,
            productType: this.editData.detail[i].productType?.id,
            product: this.editData.detail[i].product?.id,
            productList: [],
            model: this.editData.detail[i].model,
            serial: this.editData.detail[i].serial,
            referenceNo: this.editData.detail[i].referenceNo
          });
        }
      },
        (error: any) => {

        });
  }

  getAllProductType(): void {
    this.genericService.getData('product/get-product-type-by-nature/Sale', true)
      .subscribe((data: any) => {
        this.productTypes = data;
      },
        (error: any) => {

        });
  }

  getProductsByType(event: any, index: any): void {
    this.genericService.getData('product/get-products-by-type/' + event, true)
      .subscribe((data: any) => {
        this.getDetail.at(index).patchValue({
          productList: data.response
        });
      },
        (error: any) => {

        });
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

  get getDetail(): FormArray {
    return this.addForm.get('detail') as FormArray;
  }

  addDetail(): void {
    this.getDetail.push(this.formBuilder.group(
      {
        id: [''],
        productType: ['', Validators.required],
        productList: [],
        model: ['', Validators.required],
        product: ['', Validators.required],
        serial: ['', Validators.required],
        referenceNo: [''],
        mudulerSerial: this.formBuilder.array([])
      }
    ));
  }

  removeDetail(index: number): void {
    if (this.getDetail.length > 1) {
      this.getDetail.removeAt(index);
    }
  }

  getMuduler(index: any): FormArray {
    let dtl = this.addForm.get('detail') as FormArray;
    return dtl.at(index).get('mudulerSerial') as FormArray;
  }

  selectedProduct(event: any, index: any) {
    this.getMuduler(index).clear();
    this.getDetail.at(index).patchValue({
      model: ''
    });
    for (let prod of this.addForm.value.detail[index].productList) {
      if (prod.id === event) {
        this.getDetail.at(index).patchValue({
          model: prod.name
        });
      }
      if (prod.id === event && prod?.mudulers) {
        for (let mud of prod?.mudulers) {
          this.getMuduler(index).push(this.formBuilder.group(
            {
              id: [mud.id],
              name: [mud.title, Validators.required],
              serial: ['', Validators.required],
            }
          ));
        }
      }
    }
  }

  onSubmit() {
    if (this.addForm.valid) {
      const obj = {
        id: undefined,
        customer: undefined,
        date: undefined,
        detail: undefined
      };
      this.id ? obj.id = this.id : delete obj.id;
      obj.date = this.addForm.value.date;
      obj.customer = this.addForm.value.customer;
      let listofSerial: any = [];
      let isDuplicateSerial = false;
      this.addForm.value.detail.map((data: any, index: number) => {
        if (listofSerial.indexOf(data.serial) !== -1) {
          isDuplicateSerial = true;
        }
        listofSerial.push(data.serial);
        if (data.mudulerSerial.length > 0) {
          for (let obj of data.mudulerSerial) {
            if (listofSerial.indexOf(obj.serial) !== -1) {
              isDuplicateSerial = true;
            }
            listofSerial.push(obj.serial);
          }
        }
      });

      if (isDuplicateSerial) {
        this.toaster.error('Duplicate Serial Number found');
        return;
      }
      obj.detail = this.addForm.value.detail;
      // console.log(obj.detail);
      this.genericService.saveData('business/save-sales', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.router.navigate(['sale']);
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
