import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RequestSessionComponent} from "./request-session/request-session.component";
import {CoachMysessionsComponent} from "./coach-my-sessions/coach-mysessions.component";
import {CoacheeMySessionsComponent} from "./coachee-my-sessions/coachee-my-sessions.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateModule} from "@ngx-translate/core";
import {FindACoachComponent} from "./find-a-coach/find-a-coach.component";
import {RouterModule} from "@angular/router";


@NgModule({
  declarations: [
    RequestSessionComponent,
    CoachMysessionsComponent,
    CoacheeMySessionsComponent,
    FindACoachComponent
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
