import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { GenericService } from 'src/app/services/generic/generic.service';

@Component({
  selector: 'app-assign-site',
  templateUrl: './assign-site.component.html',
  styleUrls: ['./assign-site.component.css']
})
export class AssignSiteComponent {
  allUsers: any;
  sites: any;
  user: any;

  constructor(public genericService: GenericService,
    private toaster: ToastrService) {
  }

  ngOnInit(): void {
    this.user = '';
    this.getAllUsers();
    this.getAllSites();
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

  getAllSites(): void {
    this.genericService.getData('right/get-site',true)
      .subscribe(
        data => {
          this.sites = data;
        },
        error => {
        });
  }

  checkAll(e: any): any {
    for (const child of this.sites?.response) {
      child.isChecked = e.target.checked ? 'Y' : 'N';
    }
  }

  onCheck(parent: any, e: any): any {
    const flag = e.target.checked ? 'Y' : 'N';
    parent.isChecked = flag;
  }

  onSave(): any {
    if (this.user !== '') {
      let assignsitesArray = [];
      for (const site of this.sites?.response) {
        if (site.isChecked !== null && site.isChecked !== 'N') {
          const object =
          {
            userId: this.user,
            siteId: site.id
          };
          assignsitesArray.push(object);
        }
      }
      if (assignsitesArray.length > 0) {
        this.genericService.saveData('auth/assign-sites', assignsitesArray)
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
      this.toaster.error('Please Select Site.');
    }
  }

  getAllSitesByUser(object: any): any {
    if (object !== '') {
      let selectedUser
      for (const user of this.allUsers?.response) {
        if (user.id === object) {
          selectedUser = user;
        }
      }
      for (const sites of this.sites?.response) {
        let flag = 'N'
        if (selectedUser?.sites) {
          for (const child of selectedUser?.sites) {
            if (sites.id === child) {
              flag = 'Y';
              break;
            }
          }
        }
        sites.isChecked = flag;
      }
    } else {
      for (const sites of this.sites?.response) {
        sites.isChecked = 'N';
      }
    }
  }
}
