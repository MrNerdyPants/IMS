import { Component } from '@angular/core';
import { FormGroup, FormArray, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/pages/toast.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent {

  breadCrumbItems!: Array<{}>;
  breadcrumbItemTitle: string = 'Add Prodcut';
  addForm: any;
  allCategories: any;
  allManufacturer: any;
  allUnit: any;
  allFeature: any;
  allSubCategories: any = [];
  fileNme: any;
  fileSize: any;
  fileData: any;
  fileType: any;
  base64textString: any;
  id: any;
  editData: any;
  productTypes: any = [];

  constructor(private formBuilder: FormBuilder, private toaster: ToastService,
    //  private ngxService: NgxUiLoaderService,
    private genericService: GenericService, private router: Router, private route: ActivatedRoute) {
  }

  get getParameters(): FormArray {
    return this.addForm.get('parameters') as FormArray;
  }
  get getParts(): FormArray {
    return this.addForm.get('parts') as FormArray;
  }
  get getModelSerial(): FormArray {
    return this.addForm.get('modelSerial') as FormArray;
  }
  get getMuduler(): FormArray {
    return this.addForm.get('muduler') as FormArray;
  }

  ngOnInit(): void {
    this.breadCrumbItems = [
      { label: 'Admin Panel', active: false },
      { label: 'Add Prodcut', active: true }
    ];
    this.addForm = this.formBuilder.group({
      title: ['', Validators.required],
      category: ['', Validators.required],
      subCategory: ['', Validators.required],
      manufacturer: ['', Validators.required],
      productType: ['', Validators.required],
      productNature: [''],
      features: [''],
      description: [''],
      functionDetail: [''],
      modelTitle: ['', Validators.required],
      warranty: [''],
      documents: this.formBuilder.array([]),
      videoLinks: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: '',
            videoLink: [''],
          })
      ]),
      muduler: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: '',
            title: '',
            warranty: ''
          })
      ]),
      parts: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: '',
            title: '',
            price: '',
            life: '',
            unit: '',
            period: '',
            date: '',
            value: '',
            reading: ''
          })
      ]),
      parameters: this.formBuilder.array([
        this.formBuilder.group(
          {
            id: '',
            title: '',
            description: '',
            standerdValue: '',
            minRange: '',
            maxRange: ''
          })
      ]),
    });
    // console.log(this.route.snapshot.params['id']);
    if (this.route.snapshot.params['id'] !== ':id') {
      this.id = this.route.snapshot.params['id'];
      this.getProductById(this.route.snapshot.params['id']);
    } else {
      this.getAllCategory();
      this.getAllUnit();
    }

  }

  updateProductNature(typeId: any): void {
    console.log(this.productTypes);
    let nature = 'Asset';
    this.productTypes.forEach((element: any) => {
      if (element.id === typeId) {
        nature = element.typeNature
      }
    });
    this.addForm.patchValue({
      productNature: nature ? nature : 'Asset'
    });
  }

  getProductById(id: any) {
    this.genericService.getData('product/get-product-by-id/' + id)
      .subscribe((data: any) => {
        this.editData = data?.response;
        this.getAllCategory();
        this.getAllManufacturer(this.editData.category?.id);
        this.getAllUnit();
        this.getAllFeature(this.editData.subCategory?.id);
        let features = new Array();
        this.editData.features?.forEach((feature: any) => {
          features.push(feature?.id);
        })
        console.log(data?.response);
        this.addForm.patchValue({
          title: this.editData.name,
          category: this.editData.category?.id,
          subCategory: this.editData.subCategory?.id,
          productType: this.editData.productType?.id,
          manufacturer: this.editData.manufacturer?.id,
          features: features,
          functionDetail: this.editData.functionDetail,
          description: this.editData.description,
          modelTitle: this.editData.modelTitle,
          warranty: this.editData.warranty,
        });        
        for (let i = 0; i < this.editData.documents?.length; i++) {
          this.addDocument(
            this.editData.documents[i].id,
            this.editData.documents[i].fileData,
            this.editData.documents[i].fileNme,
            this.editData.documents[i].fileSize,
            this.editData.documents[i].contentType);
        }
        for (let i = 0; i < this.editData.videoLinks?.length; i++) {
          if (i !== 0)
            this.addVideoLink();
          this.getVideoLinks.at(i)?.patchValue({
            id: this.editData.videoLinks[i].id,
            videoLink: this.editData.videoLinks[i].videoLink
          });
        }
        for (let i = 0; i < this.editData.mudulers?.length; i++) {
          if (i !== 0)
            this.addMuduler();
          this.getMuduler.at(i)?.patchValue({
            id: this.editData.mudulers[i].id,
            title: this.editData.mudulers[i].title,
            warranty: this.editData.mudulers[i].warranty
          });
        }
        for (let i = 0; i < this.editData.parts?.length; i++) {
          if (i !== 0)
            this.addParts();
          this.getParts.at(i)?.patchValue({
            id: this.editData.parts[i].id,
            title: this.editData.parts[i].title,
            price: this.editData.parts[i].price,
            life: this.editData.parts[i].life,
            unit: this.editData.parts[i].unit,
            period: this.editData.parts[i].period,
            value: this.editData.parts[i].value,
            date: this.editData.parts[i].date,
            reading: this.editData.parts[i].reading,
          });
        }
        for (let i = 0; i < this.editData.parameters?.length; i++) {
          if (i !== 0)
            this.addParameters();
          this.getParameters.at(i)?.patchValue({
            id: this.editData.mudulers[i].id,
            title: this.editData.parameters[i].title,
            description: this.editData.parameters[i].description,
            standerdValue: this.editData.parameters[i].standerdValue,
            minRange: this.editData.parameters[i].minRange,
            maxRange: this.editData.parameters[i].maxRange,
          });
        }
      },
        (error: any) => {

        });
  }

  getAllManufacturer(catId: any): void {
    this.allManufacturer = [];
    if (catId !== '') {
      this.genericService.getData('settings/get-manufacturer-by-category/' + catId, true)
        .subscribe((data: any) => {
          this.allManufacturer = data;
          console.log(data);
        },
          (error: any) => {

          });
    }
  }

  getAllUnit(): void {
    this.genericService.getData('settings/get-unit')
      .subscribe((data: any) => {
        this.allUnit = data;
      },
        (error: any) => {

        });
  }

  getAllFeature(id: any): void {
    this.allFeature = [];
    this.addForm.patchValue({
      features: []
    });
    if (id !== '')
      this.genericService.getData('settings/get-feature-by-sub-category/' + id)
        .subscribe((data: any) => {
          this.allFeature = data;
        },
          (error: any) => {

          });
  }

  getTypes(id: any): void {
    this.productTypes = [];
    // this.addForm.patchValue({
    //   productType: ''
    // });
    if (id !== '') {
      for (let i = 0; i < this.allSubCategories.length; i++) {
        if (this.allSubCategories[i].id === id) {
          this.productTypes = this.allSubCategories[i].types;
          this.updateProductNature(this.editData.productType?.id);
        }
      }
    }
  }

  getAllCategory(): void {
    this.genericService.getData('settings/get-category-by-company', true)
      .subscribe((data: any) => {
        this.allCategories = data;
        if (this.editData) {
          this.loadSubCategory(this.editData.category?.id);
        }
      },
        (error: any) => {

        });
  }

  loadSubCategory(id: any) {
    console.log(id);
    if (id !== '') {
      this.genericService.getData('settings/get-sub-category-by-parent/' + id)
        .subscribe((data: any) => {
          this.allSubCategories = data.response;
          this.getTypes(this.editData.subCategory?.id);
        },
          (error: any) => {

          });
    } else {
      this.allSubCategories = [];
    }
  }

  onFormSubmit(): void {
    if (this.addForm.valid) {
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
        console.log(control);
      }
    });
  }

  addParameters(): void {
    this.getParameters.push(this.formBuilder.group(
      {
        id: '',
        title: '',
        description: '',
        standerdValue: '',
        minRange: '',
        maxRange: ''
      }
    ));
  }

  removeParameters(ParameterIndex: number): void {
    if (this.getParameters.length > 1) {
      this.getParameters.removeAt(ParameterIndex);
    }
  }

  addParts(): void {
    this.getParts.push(this.formBuilder.group(
      {
        id: '',
        title: '',
        price: '',
        life: '',
        unit: '',
        period: '',
        value: '',
        date: '',
        reading: ''
      }
    ));
  }

  removeParts(partIndex: number): void {
    if (this.getParts.length > 1) {
      this.getParts.removeAt(partIndex);
    }
  }

  addMuduler(): void {
    this.getMuduler.push(this.formBuilder.group(
      {
        id: '',
        title: '',
        warranty: ''
      }
    ));
  }
  removeMuduler(muduler: number): void {
    if (this.getMuduler.length > 1) {
      this.getMuduler.removeAt(muduler);
    }
  }

  get documents(): FormArray {
    return this.addForm.get('documents');
  }

  removeDocument(index: number): void {
    this.documents.removeAt(index);
  }

  onDocSelect(e: any): void {
    if (e.target.files.length > 0) {
      let file = e.target.files[0];
      if (file.size > 1024000) {
        this.toaster.error('File Size cannot exceed 1 MB Limit.');
        file = '';
        this.fileNme = null;
        this.fileSize = null;
        this.fileType = null;
      } else {
        this.fileNme = file.name;
        this.fileSize = file.size;
        this.fileType = file.type;
      }
      const fileReader = new FileReader();
      fileReader.onload = this.handleFile.bind(this);
      fileReader.readAsBinaryString(file);
      e.target.value = null;
    }
  }

  handleFile(event: any): void {
    const binaryString = event.target.result;
    this.base64textString = btoa(binaryString);
    this.fileData = btoa(binaryString);
    this.addDocument('', this.fileData, this.fileNme, this.fileSize, this.fileType);
  }

  addDocument(id: any, data: any, name: any, size: any, type: any): void {
    this.documents.push(this.formBuilder.group({
      id: id,
      fileData: data,
      fileNme: name,
      fileSize: size,
      documentDesc: '',
      contentType: type
    }));
  }


  get getVideoLinks(): FormArray {
    return this.addForm.get('videoLinks') as FormArray;
  }

  addVideoLink(): void {
    this.getVideoLinks.push(this.formBuilder.group(
      {
        id: '',
        videoLink: [''],
      }
    ));
  }

  removeVideoLink(videoLinkGroup: number): void {
    if (videoLinkGroup > 0) {
      this.getVideoLinks.removeAt(videoLinkGroup);
    }
  }
  saveProduct(): void {
    if (this.addForm.valid) {
      let product = {
        id: undefined,
        name: undefined,
        category: undefined,
        subCategory: undefined,
        manufacturer: undefined,
        productType: undefined,
        features: undefined,
        description: undefined,
        functionDetail: undefined,
        modelTitle: undefined,
        warranty: undefined,
        mudulers: undefined,
        parts: undefined,
        parameters: undefined,
        videoLinks: undefined,
        documents: undefined,
      };
      if (this.id) {
        product.id = this.id;
      } else {
        delete product.id;
      }
      product.name = this.addForm.value.title;
      product.category = this.addForm.value.category;
      product.subCategory = this.addForm.value.subCategory;
      product.manufacturer = this.addForm.value.manufacturer;
      product.productType = this.addForm.value.productType;
      this.addForm.value.features !== '' ? product.features = this.addForm.value.features : delete product.features;
      this.addForm.value.description !== '' ? product.description = this.addForm.value.description : delete product.description;
      this.addForm.value.functionDetail !== '' ? product.functionDetail = this.addForm.value.functionDetail : delete product.functionDetail;
      product.modelTitle = this.addForm.value.modelTitle;
      this.addForm.value.warranty !== '' ? product.warranty = this.addForm.value.warranty : delete product.warranty;

      this.addForm.value.documents.length > 0 ? product.documents = this.addForm.value.documents : delete product.documents;

      let videoLinks: any = []
      this.addForm.value.videoLinks.map((data: any, index: number) => {
        if (data.videoLink.trim() !== '')
          videoLinks.push(data);
      });
      if (videoLinks.length > 0) {
        product.videoLinks = videoLinks;
      } else {
        delete product.videoLinks;
      }

      let mudulers: any = []
      this.addForm.value.muduler.map((data: any, index: number) => {
        if (data.title.trim() !== '') {
          mudulers.push(data);
        }
      });
      if (mudulers.length > 0) {
        product.mudulers = mudulers;
      } else {
        delete product.mudulers;
      }
      let parts: any = [];
      this.addForm.value.parts.map((data: any, index: number) => {
        if (data.title.trim() !== '') {
          parts.push(data);
        }
      });
      if (parts.length > 0) {
        product.parts = parts;
      } else {
        delete product.parts;
      }
      let parameters: any = [];
      this.addForm.value.parameters.map((data: any, index: number) => {
        if (data.title.trim() !== '') {
          parameters.push(data);
        }
      });
      if (parameters.length > 0) {
        product.parameters = parameters;
      } else {
        delete product.parameters;
      }
      this.genericService.saveData('product', product)
        .subscribe(
          (data: any) => {
            // this.ngxService.stop();
            if (data.code === 200) {
              this.toaster.success(data.message);
              this.router.navigate(['/products']);
            } else {
              this.toaster.error(data.message);
            }

          },
          (error: any) => {
            this.toaster.error('Unexpected Error! Please check you network');
          });

    } else {
      this.toaster.warning('Please fill all the required fields and try again.');
      // this.ngxService.stop();
      this.validateAllFormFields(this.addForm);
    }
  }

  onlyCharAndSpace(e: any): void {
    let str = e.target.value;
    str = str.replace(/[^\ \a-z,A-Z]/g, '');
    e.target.value = str;
  }

  onlyChar(e: any): void {
    let str = e.target.value;
    str = str.replace(/[^\a-z,A-Z]/g, '');
    e.target.value = str;
  }

  onlyInteger(e: any): void {
    let str1 = e.target.value;
    str1 = str1.replace(/[^\d]/g, '');
    e.target.value = str1;
  }

  onlyIntegerWithMinus(e: any): void {
    let str1 = e.target.value;
    str1 = str1.replace(/[^\d\-]/g, '');
    e.target.value = str1;
  }

  onlyDouble(e: any): void {
    e.target.value = e.target.value.replace(/[^\.\d]/g, '');
  }

  onlyCharIntegers(e: any): void {
    e.target.value = e.target.value.replace(/[^\d\a-z,A-Z]/g, '');
  }

  onlyCharIntegerAndMinus(e: any): void {
    e.target.value = e.target.value.replace(/[^\d\-\a-z,A-Z]/g, '');
  }

  onlyCharWithSpace(e: any): void {
    e.target.value = e.target.value.replace(/[^\ \a-z,A-Z]/g, '');
  }

  onlyCharWithDot(e: any): void {
    e.target.value = e.target.value.replace(/[^\.\a-z,A-Z]/g, '');
  }

  onlyCharWithSpecialCharacter(e: any): void {
    e.target.value = e.target.value.replace(/[^\.\ \-\a-z,A-Z]/g, '');
  }

  alphaNumeric(e: any): void {
    e.target.value = e.target.value.replace(/[^\a-z,A-Z,\-,., ,_,&,(,),+,=,%,$,#,@,!,*,\d\/]/g, '');
  }
}
