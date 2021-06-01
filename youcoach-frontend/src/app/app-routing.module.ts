import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoginComponent} from './security/login/login.component';
import {RegisterComponent} from './security/register/register.component';
import {HomeComponent} from './template/home/home.component';
import {BecomeCoachComponent} from './profile/become-coach/become-coach.component';
import {DisplayProfileComponent} from './profile/display-profile/display-profile.component';
import {EditProfileComponent} from './profile/edit-profile/edit-profile.component';
import {CoachProfileComponent} from './profile/coach-profile/coach-profile.component';
import {ValidateAccountComponent} from './security/validate-account/validate-account.component';
import {ForbiddenComponent} from './security/forbidden/forbidden.component';
import {EmailValidationSuccessComponent} from './security/email-validation-success/email-validation-success.component';
import {RegistrationSuccessComponent} from './security/registration-success/registration-success.component';
import {PasswordResetRequestedComponent} from './security/password-reset-requested/password-reset-requested.component';
import {PasswordResetComponent} from './security/password-reset/password-reset.component';
import {PasswordChangeSuccessComponent} from './security/password-change-success/password-change-success.component';
import {PasswordChangeFailureComponent} from './security/password-change-failure/password-change-failure.component';
import {EditCoachInformationComponent} from './profile/edit-coach-information/edit-coach-information.component';
import {FindACoachComponent} from './session/find-a-coach/find-a-coach.component';
import {ChangeTopicsComponent} from './profile/change-topics/change-topics.component';
import {RequestSessionComponent} from './session/request-session/request-session.component';
import {CoacheeMySessionsComponent} from './session/coachee-my-sessions/coachee-my-sessions.component';
import {CoachMysessionsComponent} from './session/coach-my-sessions/coach-mysessions.component';
import {NotFoundComponent} from './template/not-found/not-found.component';
import {SessionDetailComponent} from "./session/session-detail/session-detail.component";
import {CoacheeComponent} from "./template/coachee/coachee.component";
import {CoachComponent} from "./template/coach/coach.component";
import {OverviewComponent} from "./admin/overview/overview.component";
import {AdminComponent} from "./template/admin/admin.component";
import {EditTopicComponent} from "./profile/edit-topic/edit-topic.component";
import {FaqComponent} from "./template/faq/faq.component";

const routes: Routes = [
  {
    path: 'coachee/:id',
    component: CoacheeComponent,
    children: [
      {path: 'profile', component: DisplayProfileComponent},
      {path: 'edit-profile', component: EditProfileComponent},
      {path: 'find-coach', component: FindACoachComponent},
      {path: 'coach-profile/:coachId', component: CoachProfileComponent},
      {path: 'coach-profile/:coachId/request-a-session', component: RequestSessionComponent},
      {path: 'sessions', component: CoacheeMySessionsComponent},
      {path: 'sessions/:sessionId', component: SessionDetailComponent},
      {path: 'becomecoach', component: BecomeCoachComponent},
    ]
  },
  {
    path: 'coach/:coachId',
    component: CoachComponent,
    children: [
      {path: 'change-topics', component: ChangeTopicsComponent},
      {path: 'coach-profile', component: CoachProfileComponent},
      {path: 'edit-topic', component: EditTopicComponent},
      {path: 'edit-coach-information', component: EditCoachInformationComponent},
      {path: 'coach-my-sessions', component: CoachMysessionsComponent},
      {path: 'sessions/:sessionId', component: SessionDetailComponent},
    ]
  },
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      {path: 'overview', component: OverviewComponent}
    ]
  },
  {path: 'home', component: HomeComponent},
  {path: 'faq', component: FaqComponent},
  {path: 'login', component: LoginComponent},
  {path: 'not-found', component: NotFoundComponent},
  {path: 'password-reset-requested', component: PasswordResetRequestedComponent},
  {path: 'password-reset', component: PasswordResetComponent},
  {path: 'password-change-success', component: PasswordChangeSuccessComponent},
  {path: 'password-change-failure', component: PasswordChangeFailureComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'registration-success', component: RegistrationSuccessComponent},
  {path: 'validate-account', component: ValidateAccountComponent},
  {path: 'email-validation-success', component: EmailValidationSuccessComponent},
  {path: 'forbidden', component: ForbiddenComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: '**', redirectTo: 'not-found', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled', anchorScrolling: 'enabled', scrollOffset: [0, 64], relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
