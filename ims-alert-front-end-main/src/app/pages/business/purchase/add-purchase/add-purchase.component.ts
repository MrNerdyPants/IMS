import { formatDate } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from 'src/app/pages/toast.service';
import { GenericService } from 'src/app/services/generic.service';

@Component({
  selector: 'app-add-purchase',
  templateUrl: './add-purchase.component.html',
  styleUrls: ['./add-purchase.component.css']
})
export class AddPurchaseComponent {
  purchaseDate: string = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  addForm: FormGroup = new FormGroup(
    {
      date: new FormControl(this.purchaseDate, Validators.required),
      vendor: new FormControl('', Validators.required),
      manufacturer: new FormControl(''),
      detail: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: [''],
            productType: ['', Validators.required],
            product: ['', Validators.required],
            model: ['', Validators.required],
            productList: [],
            qty: ['', Validators.required],
            serial: [''],
            mudulerSerial: this.formBuilder.array([])
          }
        )
      ])
    }
  );
  vendors: any;
  loading = true;
  productTypes: any;
  products: any;
  manufacturers: any;
  id: any;
  editData: any;

  constructor(private toaster: ToastService, private modalService: NgbModal,
    public genericService: GenericService, private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAllData();
    this.getAllProductType();
    this.getAllManufacturer();
    if (this.route.snapshot.params['id'] !== ':id') {
      this.id = this.route.snapshot.params['id'];
      this.getSalesById(this.route.snapshot.params['id']);
    }
  }

  getSalesById(id: any) {
    this.genericService.getData('business/get-purchase-by-id/' + id)
      .subscribe((data: any) => {
        this.editData = data?.response;
        console.log(this.editData);
        this.addForm.patchValue({
          date: formatDate(new Date(this.editData.date), 'yyyy-MM-dd', 'en'),
          vendor: this.editData?.vendor?.id,
          manufacturer: this.editData?.manufacturer?.id
        });
        for (let i = 0; i < this.editData.detail?.length; i++) {
          if (i !== 0)
            this.addDetail();
          for (let muduler of this.editData.detail[i]?.mudulerSerial) {
            this.getMuduler(i).push(this.formBuilder.group(
              {
                id: [muduler.id],
                name: [muduler.name, Validators.required],
                serial: [muduler.serial],
              }
            ));
          }
          this.getProductsByType(this.editData.detail[i]?.productType?.id, i);
          this.getDetail.at(i)?.patchValue({
            id: this.editData.detail[i].id,
            productType: this.editData.detail[i].productType?.id,
            product: this.editData.detail[i].product?.id,
            model: this.editData.detail[i].model,
            qty: this.editData.detail[i].qty,
            serial: this.editData.detail[i].serial
          });
        }
      },
        (error: any) => {

        });
  }

  getAllProductType(): void {
    this.genericService.getData('product/get-product-type', true)
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
    this.genericService.getData('settings/get-vendor', true)
      .subscribe((data: any) => {
        this.vendors = data;
        this.loading = false;
      },
        (error: any) => {

        });
  }

  getAllManufacturer(): void {
    this.genericService.getData('settings/get-manufacturer', true)
      .subscribe((data: any) => {
        this.manufacturers = data;
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
        product: ['', Validators.required],
        model: ['', Validators.required],
        productList: [],
        qty: ['', Validators.required],
        serial: [''],
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
              serial: [''],
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
        vendor: undefined,
        manufacturer: undefined,
        date: undefined,
        detail: undefined
      };
      this.id ? obj.id = this.id : delete obj.id;

      obj.date = this.addForm.value.date;
      obj.vendor = this.addForm.value.vendor;
      if (this.addForm.value.manufacturer !== '') {
        obj.manufacturer = this.addForm.value.manufacturer;
      } else {
        delete obj.manufacturer;
      }
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
      this.genericService.saveData('business/save-purchase', obj)
        .subscribe(
          (data: any) => {
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.router.navigate(['/purchase']);
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
