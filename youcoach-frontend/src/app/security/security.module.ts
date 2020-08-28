import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EmailValidationSuccessComponent} from "./email-validation-success/email-validation-success.component";
import {ForbiddenComponent} from "./forbidden/forbidden.component";
import {LoginComponent} from "./login/login.component";
import {PasswordChangeFailureComponent} from "./password-change-failure/password-change-failure.component";
import {PasswordChangeSuccessComponent} from "./password-change-success/password-change-success.component";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {PasswordResetRequestedComponent} from "./password-reset-requested/password-reset-requested.component";
import {RegisterComponent} from "./register/register.component";
import {RegistrationSuccessComponent} from "./registration-success/registration-success.component";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AuthenticationInterceptor} from "./services/authentication/authentication.interceptor";
import {ValidateAccountComponent} from "./validate-account/validate-account.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {RouterModule} from "@angular/router";
import {AuthImagePipe} from "./pipes/auth-image.pipe";
import { UpdateImageComponent } from './update-image/update-image.component';


@NgModule({
  declarations: [
    ValidateAccountComponent,
    EmailValidationSuccessComponent,
    ForbiddenComponent,
    LoginComponent,
    PasswordChangeFailureComponent,
    PasswordChangeSuccessComponent,
    PasswordResetComponent,
    PasswordResetRequestedComponent,
    RegisterComponent,
    RegistrationSuccessComponent,
    AuthImagePipe,
    UpdateImageComponent
  ],
  imports: [
    RouterModule,
    ReactiveFormsModule,
    TranslateModule,
    CommonModule,
    FormsModule
  ],
  exports: [
    AuthImagePipe,
    UpdateImageComponent
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true},
  ]
})
export class SecurityModule {
}
