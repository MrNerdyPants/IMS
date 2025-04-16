import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { AuthService } from 'src/app/services/auth/auth.service';
import { GenericService } from 'src/app/services/generic/generic.service';

@Component({
  selector: 'app-assign-groups',
  templateUrl: './assign-groups.component.html',
  styleUrls: ['./assign-groups.component.css']
})
export class AssignGroupsComponent {
  allUsers: any;
  groups: any;
  user: any;

  constructor(public genericService: GenericService,
    private toaster: ToastrService) {
  }

  ngOnInit(): void {
    this.user = '';
    this.getAllUsers();
    this.getAllGroups();
  }

  getAllUsers(): void {
    this.genericService.getData('auth/get-user')
      .subscribe(
        data => {
          this.allUsers = data;
        },
        error => {
        });
  }

  getAllGroups(): void {
    this.genericService.getData('right/get-group')
      .subscribe(
        data => {
          this.groups = data;
        },
        error => {
        });
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

  onSave(): any {
    if (this.user !== '') {
      let assignGroupsArray = [];
      for (const group of this.groups?.response) {
        if (group.isChecked !== null && group.isChecked !== 'N') {
          const object =
          {
            userId: this.user,
            groupId: group.id
          };
          assignGroupsArray.push(object);
        }
      }
      if (assignGroupsArray.length > 0) {
        this.genericService.saveData('auth/assign-groups', assignGroupsArray)
          .subscribe(
            (data: any) => {
              if(data.code === 200){
                this.toaster.success(data.message);
              }else {
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

  getAllGroupsByUser(object: any): any {
    if (object !== '') {
      let selectedUser
      for (const user of this.allUsers?.response) {
        if (user.id === object) {
          selectedUser = user;
        }
      }
      for (const groups of this.groups?.response) {
        let flag = 'N'
        if (selectedUser?.groups) {
          for (const child of selectedUser?.groups) {
            if (groups.id === child) {
              flag = 'Y';
              break;
            }
          }
        }
        groups.isChecked = flag;
      }
    } else {
      for (const groups of this.groups?.response) {
        groups.isChecked = 'N';
      }
    }
  }
}
