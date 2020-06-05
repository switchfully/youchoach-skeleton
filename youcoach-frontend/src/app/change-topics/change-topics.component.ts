import { Component, OnInit } from '@angular/core';
import {environment} from '../../environments/environment';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-change-topics',
  templateUrl: './change-topics.component.html',
  styleUrls: ['./change-topics.component.css']
})
export class ChangeTopicsComponent implements OnInit {
  private mailto: string;

  constructor(public translateService: TranslateService) { }

  ngOnInit(): void {
  }

  mail() {
    this.mailto = 'mailto:' + environment.adminEmail;

    this.translateService.get('request-profile-change.email-subject').subscribe((subject: string) => {
      this.mailto += '?subject=' + subject;
    });

    this.translateService.get('request-profile-change.email-body').subscribe((body: string) => {
      this.mailto += '&body=' + body;
    });
    location.href = this.mailto;
  }

}
