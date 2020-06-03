import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {LoginComponent} from './login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {HelloWorldComponent} from './hello-world/hello-world.component';
import {AuthenticationInterceptor} from './authentication/authentication.interceptor';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { RegisterComponent } from './register/register.component';
import {MatButtonModule} from '@angular/material/button';
import {_MatMenuDirectivesModule, MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import { HomeComponent } from './home/home.component';
import { BecomeCoachComponent } from './become-coach/become-coach.component';
import { DisplayProfileComponent } from './display-profile/display-profile.component';
import {CoachProfileComponent} from './coach-profile/coach-profile.component';
import { ProfileHeaderComponent } from './profile-header/profile-header.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ValidateAccountComponent } from './validate-account/validate-account.component';
import {RedirectOnErrorInterceptor} from './redirect-on-error.interceptor';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { EmailValidationSuccessComponent } from './email-validation-success/email-validation-success.component';
import { RegistrationSuccessComponent } from './registration-success/registration-success.component';
import { PasswordResetRequestedComponent } from './password-reset-requested/password-reset-requested.component';
import { PasswordResetComponent } from './password-reset/password-reset.component';
import { PasswordChangeSuccessComponent } from './password-change-success/password-change-success.component';
import { PasswordChangeFailureComponent } from './password-change-failure/password-change-failure.component';
import { EditCoachInformationComponent } from './coach-profile/edit-coach-information/edit-coach-information.component';
import { FindACoachComponent } from './find-a-coach/find-a-coach.component';
import { ChangeTopicsComponent } from './change-topics/change-topics.component';
import { RequestSessionComponent } from './request-session/request-session.component';
import {CustomFormsModule} from 'ngx-custom-validators';
import { CoachMysessionsComponent } from './coach-my-sessions/coach-mysessions.component';
import { CoacheeMySessionsComponent } from './coachee-my-sessions/coachee-my-sessions.component';



export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HelloWorldComponent,
    HeaderComponent,
    FooterComponent,
    RegisterComponent,
    HomeComponent,
    BecomeCoachComponent,
    DisplayProfileComponent,
    CoachProfileComponent,
    ProfileHeaderComponent,
    EditProfileComponent,
    ValidateAccountComponent,
    ForbiddenComponent,
    NotFoundComponent,
    EmailValidationSuccessComponent,
    RegistrationSuccessComponent,
    PasswordResetRequestedComponent,
    PasswordResetComponent,
    PasswordChangeSuccessComponent,
    PasswordChangeFailureComponent,
    EditCoachInformationComponent,
    FindACoachComponent,
    ChangeTopicsComponent,
    RequestSessionComponent,
    CoachMysessionsComponent,
    CoacheeMySessionsComponent,
      ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    BrowserAnimationsModule,
    MatButtonModule,
    _MatMenuDirectivesModule,
    MatMenuModule,
    MatIconModule,
    CustomFormsModule,

  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: RedirectOnErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
