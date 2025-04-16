import { Component, Input } from '@angular/core';
import { FormBuilder, FormControl, Validators, FormGroup, FormArray } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/account/toasts/toast.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  addForm: any;
  @Input() id: any;
  @Input() editData: any;
  groups: any;
  fileNme: any;
  fileSize: any;
  fileData: any;
  fileType: any;
  base64textString: any;

  constructor(private toastr: ToastService, private formBuilder: FormBuilder,
    public activeModal: NgbActiveModal, public genericService: GenericService,
    private toaster: ToastService) {
  }

  ngOnInit(): void {
    this.addForm = this.formBuilder.group({
      title: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl(''),
      confirmPassword: new FormControl(''),
    });
    console.log(this.editData);
    if (this.id) {
      this.addForm.patchValue({
        title: this.editData.name,
        username: this.editData.username,
      });
    }
    this.getAllGroups();
  }

  checkAll(e: any): any {
    for (const child of this.groups?.response) {
      child.isChecked = e.target.checked ? 'Y' : 'N';
    }
  }

  onCheck(parent: any, e: any): any {
    const flag = e.target.checked ? 'Y' : 'N';
    parent.isChecked = flag;
  }

  getAllGroups(): void {
    this.genericService.getData('right/get-group')
      .subscribe(
        data => {
          this.groups = data;
          if (this.id) {
            for (const groups of this.groups?.response) {
              let flag = 'N'
              console.log(groups);
              if (this.editData?.groups) {
                for (const child of this.editData?.groups) {
                  console.log(child);
                  if (groups.id === child?.group?.id) {
                    flag = 'Y';
                    break;
                  }
                }
              }
              groups.isChecked = flag;
            }
          }
        },
        error => {
        });
  }

  onFileSelect(event: any): void {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      if (file.size > 524288) {
        this.toaster.error('File Size cannot exceed 500 KB Limit.');
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
      event.target.value = null;
    }
  }

  handleFile(event: any): void {
    const binaryString = event.target.result;
    this.base64textString = btoa(binaryString);
    this.fileData = btoa(binaryString);
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
      let obj = {
        id: undefined,
        name: undefined,
        username: undefined,
        password: undefined,
        profile: undefined,
        fileNme: undefined,
        fileSize: undefined,
        fileType: undefined,
        groups: undefined
      };
      let assignGroupsArray: any = [];
      for (const group of this.groups?.response) {
        if (group.isChecked !== null && group.isChecked !== 'N') {
          assignGroupsArray.push(group.id);
        }
      }

      obj.groups = assignGroupsArray;

      if (this.id) {
        obj.id = this.id;
      } else {
        delete obj.id;
      }

      if (this.addForm.value.password !== '') {
        obj.password = this.addForm.value.password;
      } else {
        delete obj.password;
      }

      obj.name = this.addForm.value.title;
      obj.username = this.addForm.value.username;
      if (this.fileData) {
        obj.profile = this.fileData;
        obj.fileNme = this.fileNme;
        obj.fileSize = this.fileSize;
        obj.fileType = this.fileType;
      } else {
        delete obj.profile;
        delete obj.fileNme;
        delete obj.fileSize;
        delete obj.fileType;
      }
      this.genericService.saveData('auth/save-user', obj)
        .subscribe(
          (data: any) => {
            if(data.code === 200){
              this.toaster.success(data.message);
              this.activeModal.close('success');
            }else {
              this.toaster.error(data.message);
              this.activeModal.close('success');
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
