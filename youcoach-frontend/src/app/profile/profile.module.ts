import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ChangeTopicsComponent} from "./change-topics/change-topics.component";
import {EditCoachInformationComponent} from "./edit-coach-information/edit-coach-information.component";
import {EditProfileComponent} from "./edit-profile/edit-profile.component";
import {CoachProfileComponent} from "./coach-profile/coach-profile.component";
import {DisplayProfileComponent} from "./display-profile/display-profile.component";
import {BecomeCoachComponent} from "./become-coach/become-coach.component";
import {EditTopicComponent} from './edit-topic/edit-topic.component';
import {SecurityModule} from "../security/security.module";
import {ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    BecomeCoachComponent,
    DisplayProfileComponent,
    CoachProfileComponent,
    EditProfileComponent,
    EditCoachInformationComponent,
    ChangeTopicsComponent,
    EditTopicComponent
  ],
  imports: [
    SecurityModule,
    TranslateModule,
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class ProfileModule {
}
