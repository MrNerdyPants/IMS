import { ChangeDetectionStrategy, Component } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/account/toasts/toast.service';
import { SignUp } from './sign-up.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  SignupForm!: UntypedFormGroup;
  submitted = false;
  fieldTextType!: boolean;
  // set the current year
  year: number = new Date().getFullYear();

  constructor(private formBuilder: UntypedFormBuilder,
    private auth: AuthenticationService,
    private toast: ToastService,
    private route: Router
  ) { }

  ngOnInit(): void {
    /**
     * Form Validatyion
     */
    this.SignupForm = this.formBuilder.nonNullable.group({
      firstName: this.formBuilder.control('', Validators.required),
      lastName: this.formBuilder.control('', Validators.required),
      email: this.formBuilder.control('', [Validators.required, Validators.email]),
      phone: this.formBuilder.control('', Validators.required),
      password: this.formBuilder.control('', Validators.required),
    });

    // Password Validation set
    var myInput = document.getElementById("password-input") as HTMLInputElement;
    var letter = document.getElementById("pass-lower");
    var capital = document.getElementById("pass-upper");
    var number = document.getElementById("pass-number");
    var length = document.getElementById("pass-length");

    // When the user clicks on the password field, show the message box
    myInput.onfocus = function () {
      let input = document.getElementById("password-contain") as HTMLElement;
      input.style.display = "block"
    };

    // When the user clicks outside of the password field, hide the password-contain box
    myInput.onblur = function () {
      let input = document.getElementById("password-contain") as HTMLElement;
      input.style.display = "none"
    };

    // When the user starts to type something inside the password field
    myInput.onkeyup = function () {
      // Validate lowercase letters
      var lowerCaseLetters = /[a-z]/g;
      if (myInput.value.match(lowerCaseLetters)) {
        letter?.classList.remove("invalid");
        letter?.classList.add("valid");
      } else {
        letter?.classList.remove("valid");
        letter?.classList.add("invalid");
      }

      // Validate capital letters
      var upperCaseLetters = /[A-Z]/g;
      if (myInput.value.match(upperCaseLetters)) {
        capital?.classList.remove("invalid");
        capital?.classList.add("valid");
      } else {
        capital?.classList.remove("valid");
        capital?.classList.add("invalid");
      }

      // Validate numbers
      var numbers = /[0-9]/g;
      if (myInput.value.match(numbers)) {
        number?.classList.remove("invalid");
        number?.classList.add("valid");
      } else {
        number?.classList.remove("valid");
        number?.classList.add("invalid");
      }

      // Validate length
      if (myInput.value.length >= 8) {
        length?.classList.remove("invalid");
        length?.classList.add("valid");
      } else {
        length?.classList.remove("valid");
        length?.classList.add("invalid");
      }
    };

  }

  // convenience getter for easy access to form fields
  get f() { return this.SignupForm.controls; }

  /**
   * Form submit
   */
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.SignupForm.invalid) {
      return;
    }
    this.auth.signUp(this.SignupForm.value).subscribe({
      next: (response) => {
        console.log(response);
        this.toast.success("Registration successful");
        this.route.navigate(['/auth/login']);
      },
      error: (error) => {
        this.toast.error("Registration failed");
        console.error(error);
      }
    });
  }

  /**
   * Password Hide/Show
   */
  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }
}
