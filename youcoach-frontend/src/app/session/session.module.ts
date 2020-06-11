import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RequestSessionComponent} from "./request-session/request-session.component";
import {CoachMysessionsComponent} from "./coach-my-sessions/coach-mysessions.component";
import {CoacheeMySessionsComponent} from "./coachee-my-sessions/coachee-my-sessions.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {FindACoachComponent} from "./find-a-coach/find-a-coach.component";
import {RouterModule} from "@angular/router";
import {FilterSessionsOnStatusPipe} from "./pipes/session-status-filter.pipe";
import {SessionStatusLabelPipe} from "./pipes/session-status-label.pipe";
import { SessionDetailComponent } from './session-detail/session-detail.component';


@NgModule({
  declarations: [
    RequestSessionComponent,
    CoachMysessionsComponent,
    CoacheeMySessionsComponent,
    FindACoachComponent,
    FilterSessionsOnStatusPipe,
    SessionStatusLabelPipe,
    SessionDetailComponent
  ],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TranslateModule,
    FormsModule,
    RouterModule
  ]
})
export class SessionModule {
}
