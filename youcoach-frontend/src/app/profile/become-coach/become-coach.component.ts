import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-become-coach',
  templateUrl: './become-coach.component.html',
  styleUrls: ['./become-coach.component.css']
})
export class BecomeCoachComponent implements OnInit {
  private mailto: string;

  constructor(public translateService: TranslateService) {
  }

  mail() {
    this.mailto = 'mailto:' + environment.adminEmail;

    this.translateService.get('become-a-coach.email-subject').subscribe((subject: string) => {
      this.mailto += '?subject=' + subject;
    });

    this.translateService.get('become-a-coach.email-body').subscribe((body: string) => {
      this.mailto += '&body=' + body;
    });
    location.href = this.mailto;
  }

  ngOnInit() {
  }
}
