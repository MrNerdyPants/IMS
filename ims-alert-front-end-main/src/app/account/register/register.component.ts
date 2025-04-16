import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';

// Register Auth
// import { environment } from '../../../environments/environment';
import { AuthenticationService } from 'src/app/services/auth.service';
// import { UserProfileService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { ToastService } from '../toasts/toast.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

/**
 * Register Component
 */
export class RegisterComponent implements OnInit {

  // Login Form
  signupForm!: UntypedFormGroup;
  submitted = false;
  successmsg = false;
  error = '';
  // set the current year
  year: number = new Date().getFullYear();

  constructor(private formBuilder: UntypedFormBuilder, private router: Router,
    private authenticationService: AuthenticationService, public toastService: ToastService,
    // private userService: UserProfileService
  ) { }

  ngOnInit(): void {
    /**
     * Form Validatyion
     */
    this.signupForm = this.formBuilder.group({
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      matchPassword: ['', [Validators.required]],
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.signupForm.controls; }

  /**
   * Register submit form
   */
  onSubmit() {
    this.submitted = true;

    //Register Api
    // this.authenticationService.register(this.f['firstname'].value, this.f['lastname'].value, this.f['email'].value, this.f['password'].value, this.f['matchPassword'].value).pipe(first()).subscribe(
    //   (data: any) => {
    //     this.successmsg = true;
    //     if (this.successmsg) {
    //       this.router.navigate(['/auth/login']);
    //     }
    //   },
    //   (error: any) => {
    //     console.log(error);
    //     this.toastService.show(error, { classname: 'bg-danger text-white', delay: 15000 });
    //   });
  }
}
