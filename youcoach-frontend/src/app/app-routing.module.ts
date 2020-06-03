import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './home/home.component';
import {BecomeCoachComponent} from './become-coach/become-coach.component';
import {DisplayProfileComponent} from './display-profile/display-profile.component';
import {EditProfileComponent} from './edit-profile/edit-profile.component';
import {CoachProfileComponent} from './coach-profile/coach-profile.component';
import {ValidateAccountComponent} from './validate-account/validate-account.component';
import {ForbiddenComponent} from './forbidden/forbidden.component';
import {EmailValidationSuccessComponent} from './email-validation-success/email-validation-success.component';
import {RegistrationSuccessComponent} from './registration-success/registration-success.component';
import {PasswordResetRequestedComponent} from './password-reset-requested/password-reset-requested.component';
import {PasswordResetComponent} from './password-reset/password-reset.component';
import {PasswordChangeSuccessComponent} from './password-change-success/password-change-success.component';
import {PasswordChangeFailureComponent} from './password-change-failure/password-change-failure.component';
import {EditCoachInformationComponent} from './coach-profile/edit-coach-information/edit-coach-information.component';
import {FindACoachComponent} from './find-a-coach/find-a-coach.component';
import {ChangeTopicsComponent} from './change-topics/change-topics.component';
import {RequestSessionComponent} from './request-session/request-session.component';
import {CoacheeMySessionsComponent} from "./coachee-my-sessions/coachee-my-sessions.component";
import {CoachMysessionsComponent} from "./coach-my-sessions/coach-mysessions.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'password-reset-requested', component: PasswordResetRequestedComponent},
  {path: 'password-reset', component: PasswordResetComponent},
  {path: 'password-change-success', component: PasswordChangeSuccessComponent},
  {path: 'password-change-failure', component: PasswordChangeFailureComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'registration-success', component: RegistrationSuccessComponent},
  {path: 'becomecoach', component: BecomeCoachComponent },
  {path: 'change-topics', component: ChangeTopicsComponent },
  {path: 'profile', component: DisplayProfileComponent },
  {path: 'profile/:id', component: DisplayProfileComponent },
  {path: 'edit-profile', component: EditProfileComponent },
  {path: 'coach-profile', component: CoachProfileComponent },
  {path: 'coach-profile/:id', component: CoachProfileComponent },
  {path: 'edit-coach-information', component: EditCoachInformationComponent},
  {path: 'find-coach', component: FindACoachComponent },
  {path: 'validate-account', component: ValidateAccountComponent },
  {path: 'email-validation-success', component: EmailValidationSuccessComponent },
  {path: 'coach-profile/:id/request-a-session', component: RequestSessionComponent },
  {path: 'coach-my-sessions', component: CoachMysessionsComponent },
  {path: 'my-sessions', component: CoacheeMySessionsComponent },
  {path: 'forbidden', component: ForbiddenComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: '**', redirectTo: 'home', pathMatch: 'full'},
  {path: '**', redirectTo: 'not-found', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
