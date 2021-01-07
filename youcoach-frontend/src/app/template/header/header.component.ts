import {AfterViewInit, Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../security/services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';
import {CoacheeService} from "../../profile/services/coachee.service";
import {filter, flatMap} from "rxjs/operators";
import {InitService} from "../materialize/init.service";
import {Observable} from "rxjs";
import {IMember} from "../../profile/interfaces/IMember";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit, AfterViewInit {
  username;
  language = 'en';

  constructor(public authenticationService: AuthenticationService,
              private translate: TranslateService,
              private coacheeService: CoacheeService,
              private initService: InitService
  ) {
  }

  ngOnInit(): void {
    this.translate.use(this.translate.getBrowserLang());

    if (this.authenticationService.getUserId()) {
      this.coacheeService.getCoacheeById(this.authenticationService.getUserId()).subscribe(profile => this.username = profile.firstName)
    }

    this.authenticationService.userLoggedIn$
      .pipe(
        filter(loggedIn => loggedIn),
        flatMap(_ => this.coacheeService.getCoacheeById(this.authenticationService.getUserId()))
      )
      .subscribe(profile => this.username = profile.firstName);

    this.authenticationService.mimicUser$
      .pipe(
        filter(mimicUserId => mimicUserId != null),
        flatMap(mimicUserId => this.coacheeService.getCoacheeById(mimicUserId))
      )
      .subscribe(member => this.username = member.firstName + ' ' + member.lastName);
  }

  ngAfterViewInit(): void {
    this.initService.initDropdowns();
    this.initService.initSidenav();
  }

  switchLanguage(language: string) {
    this.translate.use(language);
    this.language = language;
  }

  currentLanguage() {
    return this.language;
  }

  logout() {
    this.authenticationService.logout();
  }

}
