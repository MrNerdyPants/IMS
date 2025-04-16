import { Component } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { AuthenticationService } from 'src/app/services/auth.service';
import { GenericService } from 'src/app/services/generic.service';
import { ToastService } from 'src/app/pages/toast.service';

@Component({
  selector: 'app-right-role',
  templateUrl: './right-role.component.html',
  styleUrls: ['./right-role.component.scss']
})
export class RightRoleComponent {
  allGroups: any;
  parentsModule: any;
  rights: any;
  group: any;

  constructor(public genericService: GenericService,
    private toaster: ToastService, private authService: AuthenticationService,
    private ngxService: NgxUiLoaderService
  ) {
  }

  ngOnInit(): void {
    this.group = '';
    this.getAllGroups();
    this.getAllParentRight();
    // this.getAllRight();
  }

  getAllGroups(): void {
    this.genericService.getData('right/get-group')
      .subscribe(
        data => {
          this.allGroups = data;
        },
        error => {
        });
  }

  getAllParentRight(): void {
    this.genericService.getData('right/get-parent-rights')
      .subscribe(
        data => {
          this.parentsModule = data;
          // console.log(data);
        },
        error => {
        });
  }

  // getAllRight(): void {
  //   this.genericService.getData('right/get-rights')
  //     .subscribe(
  //       data => {
  //         this.rights = data;
  //         console.log(data);
  //       },
  //       error => {
  //       });
  // }

  checkAll(mode: string, e: any): any {
    for (const parent of this.parentsModule?.response) {
      for (const child of parent?.children) {
        child[mode] = e.target.checked ? 'Y' : 'N';
      }
    }
  }

  onCheckboxSelected(child: { canEdit: string; canDelete: string; canExport: string; }, e: any): any {
    const flag = e.target.checked ? 'Y' : 'N';
    child.canEdit = flag;
    child.canDelete = flag;
    child.canExport = flag;
  }

  onCheck(mode: string, parent: { [x: string]: string; canEdit: string; canDelete: string; canExport: string; isChecked: string; }, e: any): any {
    const flag = e.target.checked ? 'Y' : 'N';
    if (mode === 'ALL') {
      parent.canEdit = flag;
      parent.canDelete = flag;
      parent.canExport = flag;
      parent.isChecked = flag;
    } else {
      parent[mode] = flag;
    }
  }

  onSave(): any {
    if (this.group !== '') {
      let roleRightArray = [];
      for (const parent of this.parentsModule?.response) {
        for (const child of parent?.children) {
          if (child.canEdit !== null && child.canEdit !== 'N'
            || child.canDelete !== null && child.canDelete !== 'N'
            || child.canExport !== null && child.canExport !== 'N') {
            const object =
            {
              groupId: this.group,
              rightId: child.id,
              canEdit: child?.canEdit,
              canDelete: child?.canDelete,
              canExport: child?.canExport,
            };
            roleRightArray.push(object);
          }
        }
      }
      if (roleRightArray.length > 0) {
        console.log(roleRightArray);
        this.genericService.saveData('right/save-group-right', roleRightArray)
          .subscribe(
            (data: any) => {
              if (data.code === 200) {
                this.toaster.success(data.message);
              } else {
                this.toaster.error(data.message);
              }

            },
            (error: any) => {
              this.toaster.error('Unexpected Error! Please check you network');
            });
      } else {
        this.toaster.error('Please Select At least One Option.');
      }
    } else {
      this.toaster.error('Please Select Group.');
    }
  }

  getAllRoleRightWithRoleId(): any {
    for (const parent of this.parentsModule?.response) {
      for (const child of parent?.children) {
        child.canEdit = 'N';
        child.canDelete = 'N';
        child.canExport = 'N';
        child.isChecked = 'N';
      }
    }
    if (this.group !== '') {
      this.ngxService.start();
      this.genericService.postData('right/get-group-rights', { groupIds: [this.group] })
        .subscribe(
          (data: any) => {
            if (data?.response) {
              // console.log(data?.response);
              for (const parent of this.parentsModule?.response) {
                for (const child of parent?.children) {
                  for (const groupRight of data?.response) {
                    if (groupRight?.right?.id === child.id) {
                      child.canEdit = groupRight.canEdit;
                      child.canDelete = groupRight.canDelete;
                      child.canExport = groupRight.canExport;
                    }
                  }
                }
              }
            }
            this.ngxService.stop();
          },
          (error: any) => {
            this.ngxService.stop();
          });
    }
  }

  markIt(emp: { canEdit: string; canDelete: string; canExport: string; }): any {
    let flag = false;
    if (emp.canEdit === 'Y' && emp.canDelete === 'Y' && emp.canExport === 'Y') {
      flag = true;
    }
    return flag;
  }

}
