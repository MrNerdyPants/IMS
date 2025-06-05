import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';

import { AccountRoutingModule } from './account-routing.module';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastsContainer } from './toasts/toasts-container.component';
import { SignUpComponent } from './sign-up/sign-up.component';

@NgModule({
  declarations: [
    ToastsContainer,
    RegisterComponent,
    LoginComponent,
    SignUpComponent    
  ],
  imports: [
    CommonModule,
    NgbToastModule,
    ReactiveFormsModule,
    AccountRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccountModule { }
