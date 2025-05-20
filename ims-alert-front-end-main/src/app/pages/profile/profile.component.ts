import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { GenericService } from 'src/app/services/generic.service';
import { User } from '../user/user.model';
import { ToastService } from '../toast.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProfileComponent {

  constructor(
    private genericService: GenericService,
    private toaster: ToastService
    // , private modalService: NgbModal
  ) {
  }

  addForm: FormGroup = new FormGroup(
    {
      name: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
      // address: new FormControl('', Validators.required),
    }
  );

  ngOnInit(): void {
    this.fetchUserData();
    // this.breadCrumbItems = [
    //   { label: 'Admin Panel', active: false },
    //   { label: 'Manufacturer', active: true }
    // ];
  }

  private fetchUserData(): void {
    this.genericService.getData("users/me").subscribe({
      next: (response: User) => {
        this.addForm.patchValue({
          name: response.name ?? '',
          phone: response.phoneNumber ?? '',
          // address: response. ?? ''
        })
      },
      error: () => {

      }
    })
  }

  onSubmit() {
    if (this.addForm.invalid) {
      this.addForm.markAllAsTouched(); // to show validation errors
      return;
    }
    this.genericService.putData("users/me", this.addForm.value).subscribe({
      next: (response:any) => {
        console.log(response);
        
      },
      error: () => {

      }
    })
  }

}
