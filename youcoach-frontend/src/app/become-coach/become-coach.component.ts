import { Component, OnInit } from '@angular/core';
import {InitMaterializeComponent} from '../init-materialize.component';
import {TranslateService} from '@ngx-translate/core';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-become-coach',
  templateUrl: './become-coach.component.html',
  styleUrls: ['./become-coach.component.css']
})
export class BecomeCoachComponent extends InitMaterializeComponent implements OnInit {
  private mailto: string;

  constructor(public translateService: TranslateService) {
    super();
  }

  mail() {
    this.mailto = 'mailto:' + environment.adminEmail;

    this.translateService.get('become-a-coach.email-subject').subscribe((subject: string) => {
      console.log(subject);
      this.mailto += '?subject=' + subject;
    });

    this.translateService.get('become-a-coach.email-body').subscribe((body: string) => {
      console.log(body);
      this.mailto += '&body=' + body;
    });
    location.href = this.mailto;
  }

  ngOnInit() {
  }
}
