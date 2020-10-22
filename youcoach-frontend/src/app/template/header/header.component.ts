import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../security/services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';
import * as M from 'materialize-css';
import {CoacheeService} from "../../profile/services/coachee.service";
import {flatMap} from "rxjs/operators";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {
  username;
  language = 'en';

  constructor(public authenticationService: AuthenticationService,
              private translate: TranslateService,
              private coacheeService: CoacheeService
  ) {
  }

  ngOnInit(): void {
    this.translate.use(this.translate.getBrowserLang());
    this.username = this.authenticationService.getUsername();
    this.authenticationService.userLoggedIn$.subscribe(_ => {
      this.username = this.authenticationService.getUsername();
    });

    this.authenticationService.mimicUser$
      .pipe(
        flatMap(mimicUserId => this.coacheeService.getCoacheeById(mimicUserId))
      )
      .subscribe(member => this.username = member.firstName + ' ' + member.lastName
      );
    setTimeout(() => M.AutoInit(), 1);
  }

  switchLanguage(language: string) {
    this.translate.use(language);
    this.language = language;
    setTimeout(() => M.AutoInit(), 1);
  }

  currentLanguage() {
    return this.language;
  }

  logout() {
    this.authenticationService.logout();
  }

}
