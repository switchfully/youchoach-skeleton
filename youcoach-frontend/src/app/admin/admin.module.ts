import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewComponent } from './overview/overview.component';
import {RouterModule} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    OverviewComponent
  ],
  imports: [
    TranslateModule,
    CommonModule,
    RouterModule,
    FormsModule
  ]
})
export class AdminModule { }
