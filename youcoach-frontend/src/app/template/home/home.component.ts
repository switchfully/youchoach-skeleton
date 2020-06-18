
import { Component, OnInit } from '@angular/core';
import {InitMaterializeComponent} from '../../init-materialize.component';
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent extends InitMaterializeComponent implements OnInit {
  title = 'You-Coach | Intra-School Coaching';


  constructor(public translateService: TranslateService) {
    super();
  }

  ngOnInit(): void {
    document.title = this.title;
  }

}
