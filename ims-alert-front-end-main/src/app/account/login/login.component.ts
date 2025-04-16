import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

// Login Auth
// import { environment } from '../../../environments/environment';
import { AuthenticationService } from 'src/app/services/auth.service';
import { ToastService } from '../toasts/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

/**
 * Login Component
 */
export class LoginComponent implements OnInit {

  // Login Form
  loginForm!: FormGroup;
  submitted = false;
  fieldTextType!: boolean;
  error = '';
  returnUrl!: string;
  // set the current year
  year: number = new Date().getFullYear();
  loading = false;


  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService,
    private router: Router, private route: ActivatedRoute, public toastService: ToastService) {

  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }

  /**
   * Form submit
   */
  ngOnInit(): void {
    // console.log(version)
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required || Validators.email],
      password: ['', Validators.required]
    });
  }

  ngOnDestroy(): void {
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

  handleLogin(): void {
    this.submitted = true;
    if (this.loginForm.invalid) {
      this.toastService.error('Email / Password is missing.');
      this.validateAllFormFields(this.loginForm);
      return;
    } else {
      this.loading = true;
      this.authenticationService
        .verifyLogin(this.loginForm.value.email, this.loginForm.value.password).subscribe((data) => {
          if (data.code === 200) {
            this.authenticationService.login(data.response);
            this.toastService.success('Loged in successfully.');
            // const analytics = getAnalytics();
            // logEvent(analytics, 'login_event', {
            //   user: [{email: this.loginForm.value.email, datetime: new Date().toLocaleString()}]
            // });
            this.router.navigate(['']);
          } else {
            this.toastService.error(data.message);
          }
        }, error => {
          this.toastService.error('Email/Password is incorrect.');
          this.loading = false;
        });
    }
  }

  /**
   * Password Hide/Show
   */
  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }

}
